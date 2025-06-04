import { PrismaClient } from "@prisma/client";
import { faker } from "@faker-js/faker";
import fs from "fs";
import mysql from "mysql2/promise";
import { config } from "dotenv";
config();

// create the connection to database

import { randomUUID } from "crypto";
import axios from "axios";
const prisma = new PrismaClient();
let listProvince = [];
async function fetchProvince() {
  const response = await axios.get("http://localhost:8000/data/tinh_tp.json");
  return Object.values(response.data);
}

async function fetchDistrict(provinceCode) {
  const response = await axios.get("http://localhost:8000/data/quan-huyen/" + provinceCode + ".json");
  return Object.values(response.data);
}

async function fetchWard(districtCode) {
  const response = await axios.get("http://localhost:8000/data/xa-phuong/" + districtCode + ".json");
  return Object.values(response.data);
}

async function createRandomAddress() {
  const { name: provinceName, code: provinceCode } = faker.helpers.arrayElement(listProvince);
  const { name_with_type: districtName, code: districtCode } = faker.helpers.arrayElement(
    await fetchDistrict(provinceCode)
  );

  const { name_with_type: wardName, code: wardCode } = faker.helpers.arrayElement(await fetchWard(districtCode));
  return `${faker.location.buildingNumber()}, ${wardName}, ${districtName}, ${provinceName}`;
}

async function createUser() {
  return {
    first_name: faker.person.firstName(),
    last_name: faker.person.lastName(),
    email: faker.internet.email(),
    password_hash: "$2a$10$tTDU8Weiq3yDt3bpjJlPnehNAR/L/4N2jP9TwQnGEA95a9HkopjTq",
    phone_number: faker.phone.number("03########"),
    address: await createRandomAddress(),
    avatar_url: faker.image.avatar(),
    is_email_verified: true,
    is_account_enabled: faker.number.int({ min: 0, max: 20 }) == 0 ? false : true,
    is_customer: true,
    created_at: faker.date.past({
      refDate: new Date(),
      years: 2,
    }),
    user_id: randomUUID(),
  };
}

(async () => {
  listProvince = await fetchProvince();
  const connection = await mysql.createConnection({
    uri: process.env.DATABASE_URL,
  });
  // create 1000 users
  await prisma.$connect();
  for (let i = 0; i < 10; i++) {
    const user = await createUser();
    await prisma.user.create({
      data: user,
    });
    const sql = `INSERT INTO user_roles(user_id, normalized_name) VALUES ('${user.user_id}', 'ROLE_CUSTOMER')`;
    await connection.execute(sql);
  }
  await prisma.$disconnect();
  await connection.end();
})().then(() => {
  console.log("done");
});
