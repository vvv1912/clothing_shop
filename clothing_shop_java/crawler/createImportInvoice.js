import { PrismaClient } from "@prisma/client";
import { faker } from "@faker-js/faker";
import fs from "fs";
import mysql from "mysql2/promise";
import { config } from "dotenv";
import { randomUUID } from "crypto";

config();
const prisma = new PrismaClient();
import axios from "axios";
let listProvince = [
  {
    name: "Hồ Chí Minh",
    slug: "ho-chi-minh",
    type: "thanh-pho",
    name_with_type: "Thành phố Hồ Chí Minh",
    code: "79",
  },
];

async function fetchDistrict(provinceCode) {
  const response = await axios.get("http://localhost:8000/data/quan-huyen/" + provinceCode + ".json");
  return Object.values(response.data);
}

async function fetchWard(districtCode) {
  const response = await axios.get("http://localhost:8000/data/xa-phuong/" + districtCode + ".json");
  return Object.values(response.data);
}

export async function createRandomAddress() {
  const { name: provinceName, code: provinceCode } = listProvince[0];
  const { name_with_type: districtName, code: districtCode } = faker.helpers.arrayElement(
    await fetchDistrict(provinceCode)
  );

  const { name_with_type: wardName, code: wardCode } = faker.helpers.arrayElement(await fetchWard(districtCode));
  return `${faker.location.buildingNumber()}, ${wardName}, ${districtName}, ${provinceName}`;
}

const createSupplier = async () => {
  const listSupplier = [];
  for (let i = 0; i < 10; i++) {
    const supplier = {
      name: "Nhà cung cấp 00" + i,
      address: await createRandomAddress(),
      phone: faker.phone.number("03########"),
      email: faker.internet.email(),
      description: faker.lorem.sentences({ max: 3 }),
    };
    listSupplier.push(supplier);
  }
  await prisma.supplier.createMany({
    data: listSupplier,
  });
};

const createImportInvoice = async () => {
  const listSupplierIds = (await prisma.supplier.findMany({})).map((supplier) => supplier.supplier_id);
  const listImportInvoiceItem = [];
  const listProductOptionsId = (await prisma.product_option.findMany({})).map((productOption) => ({
    product_option_id: productOption.product_option_id,
    productId: productOption.product_product_id,
  }));
  for (const supplierId of listSupplierIds) {
    let newImportInvoice = {
      supplier_supplier_id: supplierId,
      total: 0,
      note: faker.lorem.sentences({ max: 2 }),
      created_date: faker.date.past({
        refDate: new Date(),
        years: 2,
      }),
    };
    newImportInvoice = await prisma.stock_receipt.create({
      data: newImportInvoice,
    });

    const numberOfImportInvoiceItem = faker.number.int({ min: 1, max: 7 });
    const setProductOptionId = new Set();
    let total = 0;
    for (let i = 0; i < numberOfImportInvoiceItem; i++) {
      const productOption = faker.helpers.arrayElement(listProductOptionsId);
      if (setProductOptionId.has(productOption.product_option_id)) continue;
      setProductOptionId.add(productOption.product_option_id);

      const product = await prisma.product.findUnique({
        where: {
          product_id: productOption.productId,
        },
      });
      const max = product.price - 50000 > 50000 ? product.price - 50000 : 60000;
      const price = faker.number.int({ min: 50000, max });
      // remove last 3 digits
      const newPrice = Math.floor(price / 100) * 100;
      const importInvoiceItem = {
        price: newPrice,
        quantity: faker.number.int({ min: 5, max: 20 }),
        product_option_id: productOption.product_option_id,
        stock_receipt_id: newImportInvoice.stock_receipt_id,
      };
      total += importInvoiceItem.price * importInvoiceItem.quantity;
      listImportInvoiceItem.push(importInvoiceItem);
      await prisma.stock_receipt.update({
        where: {
          stock_receipt_id: newImportInvoice.stock_receipt_id,
        },
        data: {
          total,
        },
      });
    }
  }
  await prisma.stock_receipt_item.createMany({
    data: listImportInvoiceItem,
  });
};

(async () => {
  await prisma.$connect();
  // await createSupplier();
  await createImportInvoice();
  await prisma.$disconnect();
})();
