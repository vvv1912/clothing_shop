import { PrismaClient } from "@prisma/client";
import { faker } from "@faker-js/faker";
import fs from "fs";
import mysql from "mysql2/promise";
import { config } from "dotenv";
import { randomUUID } from "crypto";
config();
const prisma = new PrismaClient();

(async () => {
  // find all order has status 3 and user_id not null
  const listOrder = await prisma.order.findMany({
    where: {
      status: 3,
      user_user_id: {
        not: null,
      },
    },
    include: {
      order_item: true,
    },
  });
  const listRating = [];

  for (const order of listOrder) {
    for (const orderItem of order.order_item) {
      let isLowRating = faker.number.int({ min: 0, max: 15 }) == 0;
      const rating = {
        content: faker.lorem.sentences({ max: 2 }),
        value: isLowRating ? faker.number.int({ min: 1, max: 3 }) : faker.number.int({ min: 4, max: 5 }),
        user_user_id: order.user_user_id,
        product_option_product_option_id: orderItem.product_option_id,
        created_date: faker.date.soon({
          days: 7,
          refDate: order.created_date,
        }),
        order_order_id: order.order_id,
      };
      listRating.push(rating);
    }
  }
  await prisma.rating.createMany({
    data: listRating,
  });
})();
