function highlightSize(element) {
    // Xóa lớp 'highlighted' khỏi tất cả các phần tử
    var sizeOptions = document.querySelectorAll('.text-sm.font-medium.text-gray-500');
    sizeOptions.forEach(function(option) {
      option.classList.remove('highlighted');
    });
    event.preventDefault();
    // Thêm lớp 'highlighted' vào phần tử được nhấp vào
    element.classList.add('highlighted');
  }
  function highlightColor(element) {
    var colorButtons = document.querySelectorAll('.flex.w-full.h-auto.ml-5.mt-4.gap-3 button');
    colorButtons.forEach(function(button) {
      button.classList.remove('highlighted');
    });
    event.preventDefault();
    element.classList.add('highlighted');
  }

  var rating_reply = document.getElementById('rating_reply');
  var rating_reply_form = document.getElementById('rating_reply_form');
  var product_details = document.getElementById('product_details');
  var product_details_form = document.getElementById('product_details_form');
  rating_reply.addEventListener('click', ()=>{
    rating_reply_form.classList.remove('hidden')
    product_details_form.classList.add('hidden')
  })
  product_details.addEventListener('click', ()=>{
    product_details_form.classList.remove('hidden')
    rating_reply_form.classList.add('hidden')
  })

  