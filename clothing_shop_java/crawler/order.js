import { PrismaClient } from "@prisma/client";
import { faker } from "@faker-js/faker";
import fs from "fs";
import mysql from "mysql2/promise";
import { config } from "dotenv";
import { randomUUID } from "crypto";
config();
const prisma = new PrismaClient();

(async () => {
  const listUser = await prisma.user.findMany({
    where: {
      is_customer: true,
    },
  });
  const listProductOption = await prisma.product_option.findMany({});
  const listOrders = [];
  const listOrderItems = [];
  for (const user of listUser) {
    if (faker.number.int({ min: 0, max: 15 }) == 0) continue;
    const numOfOrder = faker.number.int({ min: 0, max: 10 });
    for (let i = 0; i < numOfOrder; i++) {
      const numOfOrderItem = faker.number.int({ min: 1, max: 5 });
      const orderItems = [];
      let totalAmount = 0;
      const id = randomUUID();

      for (let j = 0; j < numOfOrderItem; j++) {
        const productOption = faker.helpers.arrayElement(listProductOption);
        // check if already exist
        const exist = orderItems.find((item) => item.product_option_id == productOption.product_option_id);
        if (exist) {
          continue;
        }
        let max = 2;
        if (faker.number.int({ min: 0, max: 15 }) == 0) {
          max = 5;
        }
        const quantity = faker.number.int({ min: 1, max });
        const product = await findProductById(productOption.product_product_id);
        const rawPrice = product.price;
        const price = Math.round(rawPrice * (1 - product.discount / 100));
        console.log(price);
        orderItems.push({
          order_id: id,
          product_option_id: productOption.product_option_id,
          price,
          quantity,
        });
        totalAmount += price * quantity;
      }
      const paymentMethod = faker.helpers.arrayElement([0, 1, 2]);
      const status = faker.helpers.arrayElement([0, 1, 2, 3, 4]);
      const deliveryFee = randomVietNameseMoney({ min: 10000, max: 40000 });
      var created_date = faker.date.past({
        refDate: new Date(),
        years: 1,
      });

      const order = {
        order_id: id,
        address: user.address,
        customer_name: user.first_name + " " + user.last_name,
        delivery_fee: deliveryFee,
        note: faker.lorem.sentences({ max: 2 }),
        payment_method: paymentMethod,
        phone_number: user.phone_number,
        status: status,
        total_amount: totalAmount + deliveryFee,
        user_user_id: user.user_id,
        email: user.email,
        completed_date: status === 3 ? faker.date.soon({ refDate: created_date, days: 10 }) : null,
        created_date: created_date,
      };
      listOrders.push(order);
      listOrderItems.push(...orderItems);
    }
  }

  await prisma.order.createMany({
    data: listOrders,
  });
  await prisma.order_item.createMany({
    data: listOrderItems,
  });
  console.log("done");
})().then(() => {
  createFakePayment();
});

async function findProductById(id) {
  const product = await prisma.product.findUnique({
    where: {
      product_id: id,
    },
  });
  return product;
}

function randomVietNameseMoney({ min = 0, max = 100000000 }) {
  const amount = faker.number.int({ min, max });
  // remove last 3 digits
  const amountString = amount.toString();
  const length = amountString.length;
  const newAmountString = amountString.slice(0, length - 3);
  const newAmount = Number(newAmountString + "000");
  return newAmount;
}

async function createFakePayment() {
  const listOrders = await prisma.order.findMany({});
  const listPayment = [];
  for (const order of listOrders) {
    const isFailed = faker.number.int({ min: 0, max: 6 }) == 0;
    const refDate = faker.date.soon({
      refDate: order.created_date,
      days: 2,
    });
    if (isFailed) {
      const payment = {
        payment_id: randomUUID(),
        amount: order.total_amount,
        status: 4,
        order_order_id: order.order_id,
        created_date: refDate,
      };
      listPayment.push(payment);
    }
    if (order.status == 4) continue;
    const created_date = faker.date.soon({
      refDate: refDate,
      days: 2,
    });
    order.completed_date = order.status == 3 ? faker.date.soon({ refDate: order.created_date, days: 10 }) : null;
    let status = 1;
    if (order.status == 3) {
      // new don hang bi huy thi payment bi huy
      status = 2;
    }
    if (order.payment_method == 0 && order.status != 3) {
      // thanh toan khi nhan hang
      status = 4; //pending
    }
    // neu don hang da thanh toan thi payment da thanh cong
    if (order.status == 2) {
      status = 1;
    }

    const payment = {
      payment_id: randomUUID(),
      amount: order.total_amount,
      status: status,
      order_order_id: order.order_id,
      created_date: created_date,
      completed_date:
        order.payment_method != 0
          ? new Date(created_date.getTime() + faker.number.int({ min: 1, max: 5 }) * 60000)
          : order.completed_date,
    };
    listPayment.push(payment);
  }
  await prisma.payment.createMany({
    data: listPayment,
  });
}
