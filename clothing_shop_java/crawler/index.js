import { PrismaClient } from "@prisma/client";
import axios from "axios";
import { load } from "cheerio";
// const prisma = new PrismaClient();
import fs from "fs";
const nameSet = new Set();
async function getList(page) {
  const { data } = await axios.get("https://www.coolmate.me/spotlight?keyword=&is_ajax=true&page=" + page);
  const $ = load(data.html);
  var cells = $(".grid__column");
  var result = [];
  cells.each((index, element) => {
    var cell = $(element);
    var imgClass = ".product-grid__image > img";
    var detailHrefClass = ".product-grid__image > a";
    var priceClass = ".product-prices > del";
    var afterDiscountPriceClass = ".product-prices > ins";
    var discountClass = ".product-prices > span";
    var name = cell.find(".product-grid__title > a").text();
    var imgURL = cell.find(imgClass).attr("src");
    var detailHref = cell.find(detailHrefClass).attr("href");
    var price = cell.find(priceClass).text();
    var afterDiscountPrice = cell.find(afterDiscountPriceClass).text();
    var discount = cell.find(discountClass).text();
    if (nameSet.has(name)) return;
    result.push({
      imgURL,
      detailHref,
      price,
      afterDiscountPrice,
      discount,
      name,
    });
    nameSet.add(name);
  });
  return result;
}

async function main() {
  var result = [];
  for (var i = 1; i <= 120; i++) {
    var list = await getList(i);
    result = [...result, ...list];
  }
  console.log(result.length);
  fs.writeFileSync("result.json", JSON.stringify(result));
}

main().then(() => {
  console.log("done");
});
