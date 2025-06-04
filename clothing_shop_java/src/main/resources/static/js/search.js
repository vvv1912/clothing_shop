// đưa sản phẩm vào
var productList = [
    {
        nameprd: "T-Shirt",
        style: "T-Shirts",
        price: "$100",
        coler: "Green",
        size: "XX_Large",
        star: "5",
        linkimg:
            'https://contents.mediadecathlon.com/p2397050/d84f0dd61fba90d0646a1a61de1be7c9/p2397050.jpg?f=1000x1000&format=auto" alt="Mô tả hình ảnh',
    },
    {
        nameprd: "T-Shirt1",
        style: "T-Shirts",
        price: "$95",
        coler: "Blue",
        size: "XX_Large",
        star: "4",
        linkimg:
            "https://contents.mediadecathlon.com/p1813630/89b404e758d8237eb0ae87f8dd19ee1b/p1813630.jpg?f=650x650&format=auto",
    },
    {
        nameprd: "T-Shirt2",
        style: "T-Shirts",
        price: "$145",
        coler: "Black",
        size: "XX_Large",
        star: "2",
        linkimg:
            "https://contents.mediadecathlon.com/p1974208/b3fcbe6f09e603be4abb625d7f4a0dcb/p1974208.jpg?f=650x650&format=auto",
    },
    {
        nameprd: "T-Shirt3",
        style: "T-Shirts",
        price: "$20",
        star: "1",
        linkimg:
            "https://contents.mediadecathlon.com/p2157315/f7e6bac4b706a58cb6d47203ece9234c/p2157315.jpg?f=650x650&format=auto",
    },
    {
        nameprd: "Shorts",
        style: "Shorts",
        price: "$145",
        coler: "Green",
        size: "XX_Large",
        star: "4",
        linkimg:
            "https://bizweb.dktcdn.net/thumb/1024x1024/100/376/467/products/3-57c3d787-5a1e-4a83-99b3-8f1ceb42ddf1.jpg?v=1633431165753",
    },
    {
        nameprd: "S RUNNING PANTS MS169",
        style: "Shorts",
        price: "$145",
        coler: "Red",
        size: "XX_Large",
        star: "4",
        linkimg:
            "https://bizweb.dktcdn.net/100/376/467/products/2-774f74e4-48f7-4c1e-9047-3fed732819a4.jpg?v=1672798605717",
    },
    {
        nameprd: "RUN FASTER MS175 RUNNING PANTS",
        style: "Shorts",
        price: "$145",
        coler: "Grean",
        size: "XX_Large",
        star: "4",
        linkimg:
            "https://bizweb.dktcdn.net/thumb/1024x1024/100/376/467/products/z3768895084431-94eb74308e3949f99db6ccdc912ffddf.jpg?v=xx",
    },
    {
        nameprd: "GAME PATTERN HOODIE",
        style: "Hoodie",
        price: "$55",
        coler: "Black",
        size: "XX_Large",
        star: "4",
        linkimg:
            "https://product.hstatic.net/200000258387/product/6g9om7r3-1-4kbm-hinh_mat_truoc-0_08d7b14ec09d46059d10e38a7b7b8ea0_master.jpg",
    },
    {
        nameprd: "EMBROIDERY HOODIE # WHITE",
        style: "Hoodie",
        price: "$50",
        coler: "White",
        size: "X_Large",
        star: "4",
        linkimg: "https://product.hstatic.net/200000258387/product/6_c3d0e96bf9a440f783a7859554085144_master.jpg",
    },
];
add(productList);

function add(product) {
    var fatherElement = document.querySelector(".listproduct-list.flex.flex-wrap");

    for (var i = 0; i < product.length; i++) {
        var numberstars = "";

        for (var z = 0; z < parseInt(product[i].star); z++) {
            numberstars +=
                '<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="starin w-6 h-6"><path stroke-linecap="round" stroke-linejoin="round" d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" /></svg>';
        }

        const htmlCode =
            '<div class="listproduct-product mt-4 ml-5 h-400px w-300px bg-white" >' +
            "<div>" +
            '    <img class="h-70pt w-100pt rounded-2xl"src="' +
            product[i].linkimg +
            '">' +
            "</div>" +
            '<div class="">' +
            '<p class="font-bold mt2">' +
            product[i].nameprd +
            "</p>" +
            '<ul class="star flex mt-2">' +
            '    <ul class=" flex">' +
            numberstars +
            " </ul>" +
            '     <p class="ml-1 ">' +
            product[i].star +
            ".0</p>" +
            '   <p class=" text-gray-500">/5</p>' +
            " </ul>   " +
            ' <p class="price font-bold text-2xl mt-2">' +
            "   " +
            product[i].price +
            "" +
            " </p>  " +
            " </div>" +
            " </div>";

        var helloElement = document.createRange().createContextualFragment(htmlCode);
        fatherElement.appendChild(helloElement);
    }
}

// đưa danh sách page
document.addEventListener("DOMContentLoaded", function () {
    var fatherElement = document.querySelector(".number-page-list.flex");

    for (var i = 0; i < 3; i++) {
        const htmlCode =
            '<li class="h-9 w-9 ml-1 hover:bg-gray-600 rounded-lg text-center py-1 t text-gray-400">' + i + "</li>";
        var helloElement = document.createRange().createContextualFragment(htmlCode);
        fatherElement.appendChild(helloElement);
    }
    const htmlCode = '<li class="h-9 w-9 ml-1 hover:bg-gray-600 rounded-lg text-center py-1 t text-gray-400">...</li>';
    var helloElement = document.createRange().createContextualFragment(htmlCode);
    fatherElement.appendChild(helloElement);
    for (var i = 7; i < 10; i++) {
        const htmlCode =
            '<li class="h-9 w-9 ml-1 hover:bg-gray-600 rounded-lg text-center py-1 t text-gray-400">' + i + "</li>";
        var helloElement = document.createRange().createContextualFragment(htmlCode);
        fatherElement.appendChild(helloElement);
    }
});

//đưa text cuối trang
var listtext = [
    {text1: "COMPANY", text2: "About", text3: "Features", text4: "Works", text5: "Career"},
    {
        text1: "HELP",
        text2: "Customer Support",
        text3: " Delivery Details",
        text4: "Terms & Conditions",
        text5: "Privacy Policy",
    },
    {text1: "FAQ", text2: "Account", text3: ",Manage Deliveries", text4: "Orders", text5: "Payments"},
    {
        text1: "RRESOURCES",
        text2: "Free eBooks",
        text3: "Development Tutorial",
        text4: "How to - Blog",
        text5: "Youtobe Playlist",
    },
];
document.addEventListener("DOMContentLoaded", function () {
    var fatherElement = document.querySelector(".textfinal.flex");

    for (var i = 0; i < listtext.length; i++) {
        const htmlCode =
            '<div class="ml-20">' +
            '<ul class="">' +
            '<li class=" text-2xl mt-2">' +
            listtext[i].text1 +
            "</li>" +
            '<li class="text-lg mt-2">' +
            listtext[i].text2 +
            "</li>" +
            '<li class="text-lg mt-2">' +
            listtext[i].text3 +
            "</li>" +
            '<li class="text-lg mt-2">' +
            listtext[i].text4 +
            "</li>" +
            '<li class="text-lg mt-2">' +
            listtext[i].text5 +
            "</li>" +
            "  </ul>" +
            "</div>";

        var helloElement = document.createRange().createContextualFragment(htmlCode);
        fatherElement.appendChild(helloElement);
    }
});
// đưa loại sản phẩm ở filter
var listtypeproduct = [{l: "T-Shirts"}, {l: "Shorts"}, {l: "Shirts"}, {l: "Hoodie"}, {l: "Jeans"}];
document.addEventListener("DOMContentLoaded", function () {
    var fatherElement = document.querySelector(".CustomizeProducts-typeClothesmt-import");

    for (var i = 0; i < listtypeproduct.length; i++) {
        const htmlCode =
            '<ul class="flex">' +
            '<p class="w-20" onclick="importTypeProduct(\'' +
            listtypeproduct[i].l +
            "') +applyfilter() \">" +
            listtypeproduct[i].l +
            "</p>" +
            '<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 ml-auto mr-2">' +
            '<path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />' +
            "</svg>" +
            "</ul>";

        var helloElement = document.createRange().createContextualFragment(htmlCode);
        fatherElement.appendChild(helloElement);
    }
});
// đưa danh sách màu
var listcolor = [
    {l: "Green"},
    {l: "Red"},
    {l: "Yellow"},
    {l: "Orange"},
    {l: "Dark Blue"},
    {l: "Blue"},
    {l: "Purple"},
    {l: "Pink"},
    {l: "Black"},
    {l: "White"},
    {l: "Grean"},
];
const colorFetch = new Promise((resolve, reject) => {
    fetch("/api/color")
        .then((response) => response.json())
        .then((data) => {
            listcolor = data.map((item) => {
                return {l: item.name, id: item.colorId};
            });
            resolve();
        });
});
document.addEventListener("DOMContentLoaded", async function () {
    var fatherElement = document.querySelector(".listcoler.flex.flex-wrap");
    await colorFetch;
    console.log(listcolor);
    for (var i = 0; i < listcolor.length; i++) {
        const htmlCode =
            "<li onclick=\"importTypeColer('" +
            listcolor[i].l +
            '\')+applyfilter()" class="bg-gray-600 rounded-full text-white flex items-center px-3 py-1 ml-2 mt-2 hover:bg-black">' +
            listcolor[i].l +
            "</li>";

        var helloElement = document.createRange().createContextualFragment(htmlCode);
        fatherElement.appendChild(helloElement);
    }
});
// đưa danh sách size
var listsize = [
    {l: "XX_Small"},
    {l: "X_Small"},
    {l: "Small"},
    {l: "Mediun"},
    {l: "Large"},
    {l: "X_Large"},
    {l: "XX_Large"},
    {l: "3X_Large"},
    {l: "4X_Large"},
];

const sizePromise = new Promise((resolve) => {
    fetch('/api/product-option/sizes')
        .then(res => res.json())
        .then(data => {
            listsize = data.map(s => ({l: s}))
            resolve();
        })
})
document.addEventListener("DOMContentLoaded", async function () {
    var fatherElement = document.querySelector(".size.flex.flex-wrap");
    await sizePromise;
    for (var i = 0; i < listsize.length; i++) {
        const htmlCode =
            "<li onclick=\"importSize('" +
            listsize[i].l +
            '\')+applyfilter()" class="bg-gray-600 rounded-full text-white flex items-center px-3 py-1 ml-2 mt-2 hover:bg-black">' +
            listsize[i].l +
            "</li>";
        var helloElement = document.createRange().createContextualFragment(htmlCode);
        fatherElement.appendChild(helloElement);
    }
});

// nhập giá trị price
var slider1 = document.getElementById("mySlider");
var output1 = document.querySelector(".kq");

slider1.oninput = function () {
    output1.innerText = "$" + slider1.value;
};

var slider2 = document.getElementById("mySlider1");
var output2 = document.querySelector(".kq1.ml-auto.mr-5");

slider2.oninput = function () {
    output2.innerText = "$" + slider2.value;
};

function importTypeProduct(value) {
    inputselect(value);
    // Thực hiện xử lý khác với giá trị nhận được
}

function importTypeColer(value) {
    inputselect(value);
    // Thực hiện xử lý khác với giá trị nhận được
}

function importSize(value) {
    inputselect(value);
    // Thực hiện xử lý khác với giá trị nhận được
}

function inputselect(value) {
    var fatherElement = document.querySelector(".Inputselect.flex.flex-wrap");

    const htmlCode =
        ' <div onclick="deleteObject.call(this)+applyfilter()" class="ext-center bg-gray-600 ml-2 mt-2 rounded-lg flex">' +
        '<li class="textinput px-6">' +
        value +
        "</li>" +
        '<svg class="absolute h-6 w-6 " xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">' +
        '<path stroke-linecap="round" stroke-linejoin="round" d="M9.75 9.75l4.5 4.5m0-4.5l-4.5 4.5M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />' +
        "</svg>" +
        " </div> ";
    var helloElement = document.createRange().createContextualFragment(htmlCode);
    fatherElement.appendChild(helloElement);
}

function deleteObject() {
    this.parentNode.removeChild(this);
}

var listinput = [];

function getTextFromListItems() {
    const listItems = document.querySelectorAll("li.textinput.px-6");
    listItems.forEach((item) => {
        const text = item.textContent;
        listinput.push(text);
    });
}

//xoa product

function xoaphantu() {
    var fatherElement = document.querySelector(".listproduct-list.flex.flex-wrap");
    var childElements = fatherElement.querySelectorAll(".listproduct-product.mt-4.ml-5.h-400px.w-300px.bg-white");
    childElements.forEach(function (child) {
        child.remove();
    });
}

//kiem tra gt co nam trong khoang
function check(value, min, max) {
    if (value >= min && value <= max) {
        return true;
    } else {
        return false;
    }
}

// lấy giá trị sau gt sau $
function laygt(str) {
    return str.substring(1);
}

//so sanh gia co thoa man
function testprice(y) {
    var kq1 = document.querySelector(".kq1.ml-auto.mr-5").innerText;
    var kq = document.querySelector(".kq").innerText;
    var x = check(parseInt(laygt(y)), laygt(kq), laygt(kq1));

    if (x == true) {
        return true;
    } else {
        return false;
    }
}

function applyfilter() {
    var listaddfillter = [];
    getTextFromListItems();
    var tampstyle = [];
    // kiểm tra lựa chọn  style
    for (var i = 0; i < listinput.length; i++) {
        for (var j = 0; j < listtypeproduct.length; j++) {
            if (listinput[i] === listtypeproduct[j].l) {
                tampstyle.push(listinput[i]);
            }
        }
    }

    // ----------------------------
    var tampsize = [];

    // kiểm tra lựa chọn  size
    for (var i = 0; i < listinput.length; i++) {
        for (var j = 0; j < listsize.length; j++) {
            if (listinput[i] === listsize[j].l) {
                tampsize.push(listinput[i]);
            }
        }
    }
    //-------------------------
    var tampcoler = [];
    // kiểm tra lựa chọn  coler
    for (var i = 0; i < listinput.length; i++) {
        for (var j = 0; j < listcolor.length; j++) {
            if (listinput[i] === listcolor[j].l) {
                tampcoler.push(listinput[i]);
            }
        }
    }

    if (listinput == "") {
        for (var j = 0; j < productList.length; j++) {
            if (testprice(productList[j].price) == true) {
                listaddfillter.push(productList[j]);
            }
        }
    } else {
        for (var j = 0; j < productList.length; j++) {
            if (testprice(productList[j].price) == true) {
                var cohieustyle = false;
                var cohieucoler = false;
                var cohieusize = false;
                for (var i = 0; i < listinput.length; i++) {
                    for (var i1 = 0; i1 < tampstyle.length; i1++) {
                        if (productList[j].style == tampstyle[i1]) {
                            var cohieustyle = true;
                            console.log(productList[j].style + "vao co");
                        }
                    }
                }
                for (var i = 0; i < listinput.length; i++) {
                    for (var i1 = 0; i1 < tampcoler.length; i1++) {
                        if (productList[j].coler == tampcoler[i1]) {
                            var cohieucoler = true;
                            console.log(productList[j].coler + "vao co coler");
                        }
                    }
                }
                for (var i = 0; i < listinput.length; i++) {
                    for (var i1 = 0; i1 < tampsize.length; i1++) {
                        if (productList[j].size == tampsize[i1]) {
                            var cohieusize = true;
                            console.log(productList[j].size + "vao co size ");
                        }
                    }
                }

                var found = false; // Thêm biến found để kiểm tra xem phần tử đã tồn tại trong listaddfillter hay chưa

                for (var i = 0; i < listinput.length; i++) {
                    if (cohieucoler == true && cohieustyle == true && cohieusize == true) {
                        var paragraph = document.getElementById("myParagraph");
                        paragraph.innerHTML = "Search For Success";
                        found = true;
                        break; // Thoát khỏi vòng lặp ngay khi tìm thấy phần tử thỏa mãn
                    } else if (cohieucoler == true && cohieustyle == true && tampsize.length == 0) {
                        var paragraph = document.getElementById("myParagraph");
                        paragraph.innerHTML = "Search For Success";
                        found = true;
                        break; // Thoát khỏi vòng lặp ngay khi tìm thấy phần tử thỏa mãn
                    } else if (cohieucoler == true && tampstyle.length == 0 && cohieusize == true) {
                        var paragraph = document.getElementById("myParagraph");
                        paragraph.innerHTML = "Search For Success";
                        found = true;
                        break; // Thoát khỏi vòng lặp ngay khi tìm thấy phần tử thỏa mãn
                    } else if (tampcoler.length == 0 && cohieustyle == true && cohieusize == true) {
                        var paragraph = document.getElementById("myParagraph");
                        paragraph.innerHTML = "Search For Success";
                        found = true;
                        break; // Thoát khỏi vòng lặp ngay khi tìm thấy phần tử thỏa mãn
                    } else if (tampcoler.length == 0 && cohieustyle == true && tampsize.length == 0) {
                        var paragraph = document.getElementById("myParagraph");
                        paragraph.innerHTML = "Search For Success";
                        found = true;
                        break; // Thoát khỏi vòng lặp ngay khi tìm thấy phần tử thỏa mãn
                    } else if (cohieucoler == true && tampstyle.length == 0 && tampsize.length == 0) {
                        var paragraph = document.getElementById("myParagraph");
                        paragraph.innerHTML = "Search For Success";
                        found = true;
                        break; // Thoát khỏi vòng lặp ngay khi tìm thấy phần tử thỏa mãn
                    } else if (tampcoler.length == 0 && tampstyle.length == 0 && cohieusize == true) {
                        var paragraph = document.getElementById("myParagraph");
                        paragraph.innerHTML = "Search For Success";
                        found = true;
                        break; // Thoát khỏi vòng lặp ngay khi tìm thấy phần tử thỏa mãn
                    }
                }
                if (found) {
                    listaddfillter.push(productList[j]);
                }
            }
        }
    }

    if (listaddfillter.length == 0) {
        var paragraph = document.getElementById("myParagraph");
        paragraph.innerHTML = "Not found";
    } else {
        xoaphantu();
        add(listaddfillter);
        console.log(listaddfillter);
    }

    listaddfillter = [];
    tampcoler = [];
    tampstyle = [];
    tampsize = [];
    listinput = [];
}

var rangeInput1 = document.getElementById("mySlider1");

rangeInput1.addEventListener("input", function () {
    // Gọi hàm khi người dùng kéo trên thẻ input
    //   applyfilter();
});
var rangeInput = document.getElementById("mySlider");

rangeInput.addEventListener("input", function () {
    // Gọi hàm khi người dùng kéo trên thẻ input
    //   applyfilter();
});
