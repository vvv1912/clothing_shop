// import fs from "fs";
// const items = JSON.parse(fs.readFileSync("result.json"));
import axios from "axios";
import { load } from "cheerio";
import { NodeHtmlMarkdown } from "node-html-markdown";
var mock = {
    detailHref: "https://www.coolmate.me/product/coolmate-x-copper-denim-quan-jeans-dang-slim-fit?color=xanh-dam",
    price: "599.000đ",
    afterDiscountPrice: "549.000đ",
    discount: "-8%",
    name: "\n                Jeans Copper Denim Slim Fit\n            ",
};
var nodeMarkdown = new NodeHtmlMarkdown();
function getSlug(href) {
    const url = new URL(href);
    const pathname = url.pathname;
    const slug = pathname.split("/").pop();
    return slug;
}
async function getDescripton(item = mock) {
    const slug = getSlug(item.detailHref);
    const { data } = await axios.get("https://www.coolmate.me/product/body-html/" + slug + "?preview=");
    const $ = load(data.html);
    return nodeMarkdown.translate($.html());
}
const nameSet = new Set();
async function getDetail(item = mock) {
    const slug = getSlug(item.detailHref);
    if (nameSet.has(slug)) return {};
    nameSet.add(slug);
    const { data } = await axios.get(item.detailHref);
    const $ = load(data);
    const discount = $(".product-single__percent-price").text().trim();
    const rawPrice = $(".product-single__compare-price").text().trim();
    const afterDiscountPrice = $(".product-single__regular-price").text().trim();
    const displayImage = $(".image > img").first().attr("src");
    const title = $(".product-single__title").text().trim();
    const category = $(".breadcrumb__item").last().text().trim();
    const colorsList = $(".option-select[class*='color'] input[name*='color']");
    const sizeList = $(".option-select[class*='size'] input[name*='size']");
    const features = $("#features-listing").html();
    const productOptions = [];

    colorsList.each((index, element) => {
        // <input type="radio" name="color" value="xanh-dam" checked="checked" data-gallery="[{&quot;id&quot;:&quot;e04a059a&quot;,&quot;src&quot;:&quot;\/image\/May2023\/Quan_Jeans_dang_Slim_Fit-thumb-1.jpg&quot;},{&quot;id&quot;:&quot;ffdc567d&quot;,&quot;src&quot;:&quot;\/image\/March2023\/Coolmate_x_Copper_Denim__Quan_Jeans_dang_Slim_Fit10.jpg&quot;},{&quot;id&quot;:&quot;8ea72825&quot;,&quot;src&quot;:&quot;\/image\/March2023\/Coolmate_x_Copper_Denim__Quan_Jeans_dang_Slim_Fit9.jpg&quot;},{&quot;id&quot;:&quot;ee396320&quot;,&quot;src&quot;:&quot;\/image\/March2023\/Coolmate_x_Copper_Jeans_-_Slimfit__-_Xanh_dam_1.jpg&quot;},{&quot;id&quot;:&quot;e45250a2&quot;,&quot;src&quot;:&quot;\/image\/March2023\/Coolmate_x_Copper_Jeans_-_Slimfit__-_Xanh_dam_5.jpg&quot;},{&quot;id&quot;:&quot;f3c7cf77&quot;,&quot;src&quot;:&quot;\/image\/March2023\/zCoolmate_x_Copper_Jeans_-_Slimfit__-_Xanh_dam_7.jpg&quot;},{&quot;id&quot;:&quot;cac481cb&quot;,&quot;src&quot;:&quot;\/image\/March2023\/Coolmate_x_Copper_Jeans_-_Slimfit__-_Xanh_dam_3.jpg&quot;},{&quot;id&quot;:&quot;587f68f8&quot;,&quot;src&quot;:&quot;\/image\/March2023\/Coolmate_x_Copper_Jeans_-_Slimfit__-_Xanh_dam_6.jpg&quot;},{&quot;id&quot;:&quot;88c0ace6&quot;,&quot;src&quot;:&quot;\/image\/March2023\/Coolmate_x_Copper_Denim__Quan_Jeans_dang_Slim_Fit12.jpg&quot;},{&quot;id&quot;:&quot;c3fd97ab&quot;,&quot;src&quot;:&quot;\/image\/March2023\/Coolmate_x_Copper_Denim__Quan_Jeans_dang_Slim_Fit14.jpg&quot;}]" data-title="Xanh đậm"></input>
        const color = $(element).attr("data-title").trim();
        var galleries = JSON.parse($(element).attr("data-gallery"));
        var gallery = [];
        galleries.forEach((item) => {
            gallery.push("https://media.coolmate.me" + item.src);
        });
        productOptions.push({
            color,
            gallery,
            size: sizeList
                .map((index, element) => {
                    return $(element).attr("data-title").trim();
                })
                .get(),
        });
    });
    if (productOptions.length == 0) {
        let gallery = $(".image > img")
            .map((index, element) => {
                return $(element).attr("src").trim();
            })
            .get();
        gallery = gallery.filter((item) => {
            return !item.includes("width=80,height=80");
        });
        productOptions.push({
            color: "",
            gallery: gallery,
            size: sizeList
                .map((index, element) => {
                    return $(element).attr("data-title").trim();
                })
                .get(),
        });
    }
    console.log("done: ", item.name);
    return {
        description: await getDescripton(item),
        discount,
        rawPrice,
        afterDiscountPrice,
        displayImage,
        title,
        category,
        productOptions,
        features: "## Tính năng \n\n-" + nodeMarkdown.translate(features).split("\n\n").join("\n\n- "),
    };
}
import fs from "fs";
async function main() {
    const items = JSON.parse(fs.readFileSync("result.json"));
    const promises = items.map((item) => {
        return getDetail(item);
    });
    const result = await Promise.all(promises);
    fs.writeFileSync("detail.json", JSON.stringify(result));
}

main().then(() => {
    console.log("done");
});
