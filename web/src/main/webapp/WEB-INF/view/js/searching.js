let currentPage = 1;
const limit = 20;
let total = 0;

const searchingInput = document.querySelector('.header-search-input');

searchingInput.addEventListener('change', handleChanging, {passive: true});

function handleChanging() {
  const categoryKey = document.getElementById('select-category').value;
  const categoryValue = searchingInput.value;
  document.querySelector('.header-search-input')

  currentPage = 1;
  removeCoupons(document.querySelectorAll('.coupon'))
  loadCoupons(currentPage, limit, categoryKey, categoryValue);
}

// window.addEventListener('scroll', () => {
//   const {
//     scrollTop,
//     scrollHeight,
//     clientHeight
//   } = document.documentElement;
//
//   if (scrollTop + clientHeight >= scrollHeight - 5 &&
//       hasMoreCoupons(currentPage, limit, total)) {
//     currentPage++;
//     removeCoupons(document.querySelectorAll('.coupon'))
//     loadCoupons(currentPage, limit);
//   }
// }, {
//   passive: true
// });
