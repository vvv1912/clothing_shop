import { PrismaClient } from "@prisma/client";
import fs from "fs";

const prisma = new PrismaClient();
async function insert(item) {
  let existCategory = await prisma.category.findFirst({
    where: {
      name: item.category.trim(),
    },
  });
  if (!existCategory) {
    existCategory = await prisma.category.create({
      data: {
        name: item.category.trim(),
        created_date: new Date(),
      },
    });
    console.log("inserted category: ", existCategory.category_id);
  }
  const existProductSlug = await prisma.product.findFirst({
    where: {
      slug: toSlug(item.title.trim()),
    },
  });
  if (existProductSlug) {
    console.log("product already exist: ", existProductSlug.product_id);
    return;
  }
  const product = await prisma.product.create({
    data: {
      description: item.features + "\n\n" + item.description,
      discount: Number(item.discount.replace(/[^0-9]/g, "")) || 0,
      price: Number(item.rawPrice.replace(/[^0-9]/g, "")) || (Math.floor(Math.random() * 500) + 50) * 1000,
      display_image: item.displayImage,
      for_gender: 0,
      name: item.title,
      total_sold: 0,
      slug: toSlug(item.title.trim()),
      category_category_id: existCategory.category_id,
      created_date: new Date(),
    },
  });
  console.log("inserted product: ", product.product_id);
  const productId = product.product_id;
  const productOptions = item.productOptions;
  const idColorMap = {};
  for await (const productOption of productOptions) {
    let existColor = await prisma.color.findFirst({
      where: {
        name: productOption.color.trim(),
      },
    });
    if (!existColor) {
      existColor = await prisma.color.create({
        data: {
          name: productOption.color.trim(),
          created_date: new Date(),
        },
      });
      console.log("inserted color: ", existColor.color_id);
    }
    idColorMap[productOption.color.trim()] = existColor.color_id;

    for await (const size of productOption.size) {
      const newProductOption = await prisma.product_option.create({
        data: {
          color_color_id: existColor.color_id,
          size: size.trim(),
          stock: Math.floor(Math.random() * 1000) + 50,
          product_product_id: product.product_id,
          created_date: new Date(),
        },
      });
    }
  }
  for await (const productOption of productOptions) {
    const id = idColorMap[productOption.color.trim()];
    for await (const image of productOption.gallery) {
      try {
        console.log("insert for product: ", productId, " color: ", id);
        await prisma.product_image.create({
          data: {
            url: image,
            for_color_color_id: id,
            created_date: new Date(),
            product_product_id: productId,
          },
        });
      } catch (e) {}
    }
  }
}

async function main() {
  const dataSTR = fs.readFileSync("detail.json");
  const data = JSON.parse(dataSTR);
  for await (const item of data) {
    await insert(item);
  }
}

main().then(() => {
  console.log("done");
});

function toSlug(str) {
  // Chuyển hết sang chữ thường
  str = str.toLowerCase();

  // xóa dấu
  str = str.replace(/(à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ)/g, "a");
  str = str.replace(/(è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ)/g, "e");
  str = str.replace(/(ì|í|ị|ỉ|ĩ)/g, "i");
  str = str.replace(/(ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ)/g, "o");
  str = str.replace(/(ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ)/g, "u");
  str = str.replace(/(ỳ|ý|ỵ|ỷ|ỹ)/g, "y");
  str = str.replace(/(đ)/g, "d");

  // Xóa ký tự đặc biệt
  str = str.replace(/([^0-9a-z-\s])/g, "");

  // Xóa khoảng trắng thay bằng ký tự -
  str = str.replace(/(\s+)/g, "-");

  // Xóa ký tự - liên tiếp
  str = str.replace(/-+/g, "-");

  // xóa phần dự - ở đầu
  str = str.replace(/^-+/g, "");

  // xóa phần dư - ở cuối
  str = str.replace(/-+$/g, "");

  // return
  return str;
}
