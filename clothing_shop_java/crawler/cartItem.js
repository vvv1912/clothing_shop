import { PrismaClient } from "@prisma/client";
import { faker } from "@faker-js/faker";
import fs from "fs";
import mysql from "mysql2/promise";
import { config } from "dotenv";
config();
const prisma = new PrismaClient();

(async () => {
  const productOptionIds = (await prisma.product_option.findMany({})).map((po) => po.product_option_id);
  const listUser = await prisma.user.findMany({
    where: {
      is_customer: true,
    },
  });
  const cartItems = [];

  for (const user of listUser) {
    const numOfCartItem = faker.number.int({ min: 0, max: 10 });
    for (let i = 0; i < numOfCartItem; i++) {
      const productOptionId = faker.helpers.arrayElement(productOptionIds);
      const isExist = cartItems.some((item) => item.product_option_id == productOptionId);
      if (isExist) continue;
      const quantity = faker.number.int({ min: 1, max: 5 });
      cartItems.push({
        product_option_id: productOptionId,
        quantity,
        user_id: user.user_id,
      });
    }
  }
  console.log(cartItems.length);
  await prisma.cart_item.createMany({
    data: cartItems,
  });
})();
