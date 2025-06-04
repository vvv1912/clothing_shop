import { PrismaClient } from "@prisma/client";
import { faker } from "@faker-js/faker";
import fs from "fs";

const prisma = new PrismaClient();

const PromotionType = {
  PERCENTAGE: 0,
  FIXED_AMOUNT: 1,
};
async function genPromotionCode() {
  const code = faker.string
    .alphanumeric({
      length: 10,
    })
    .toUpperCase();
  var newCode = {
    code,
    active: true,
    type: faker.number.int({
      min: 0,
      max: 1,
    }),
    discount: 0,
    min_order_amount: faker.number.int({
      min: 0,
      max: 100000,
    }),

    end_date: faker.date.future({
      years: 1,
      refDate: new Date().setMonth(new Date().getMonth() - 5),
    }),
    start_date: faker.date.between({
      from: new Date().setMonth(new Date().getMonth() - 1), // 1 month ago
      to: new Date().setDate(new Date().getDate() + 5),
    }),
    max_value: faker.number.int({
      min: 10000,
      max: 1000000,
    }),

    stock: faker.number.int({
      min: 1,
      max: 1000,
    }),
  };
  if (newCode.type == 0) {
    newCode.discount = faker.number.int({
      min: 10,
      max: 100,
    });
  } else {
    newCode.discount = faker.number.int({
      min: 10000,
      max: 500000,
    });
    newCode.max_value = null;
  }
  newCode.description = `Giảm ${newCode.discount}đ cho đơn hàng từ ${newCode.min_order_amount}đ, ${
    newCode.type == 0 ? "tối đa " + newCode.max_value + "đ" : ""
  }`;
  newCode.name = `Mã giảm ${newCode.type == 0 ? newCode.discount + "%" : newCode.discount + "đ"}`;

  const existCode = await prisma.promotion.findFirst({
    where: {
      code,
    },
  });
  if (existCode) {
    return await genPromotionCode();
  }
  await prisma.promotion.create({
    data: newCode,
  });
  return newCode;
}

(async () => {
  // gen promotion code 40

  await prisma.$connect();
  const countPromotion = await prisma.promotion.count();
  // delete all promotion code
  await prisma.promotion.deleteMany({});
  console.log(`count promotion: ${countPromotion}`);
  for (let i = 0; i < 40; i++) {
    await genPromotionCode();
  }
  await prisma.$disconnect();
})();
