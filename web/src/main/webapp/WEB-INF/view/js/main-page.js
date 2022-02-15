const limit = 20;
const couponsEl = document.querySelector('.coupons');
let currentPage = 1;
let total = 0;
let categoryKey = ''
let categoryValue = ''

const loaderEl = document.querySelector('.lds-roller');
const hideLoader = () => {
  loaderEl.classList.remove('show')

};
const showLoader = () => {
  loaderEl.classList.add('show')

}

window.addEventListener('scroll', () => {
  const {
    scrollTop,
    scrollHeight,
    clientHeight
  } = document.documentElement;

  if (scrollTop + clientHeight >= scrollHeight - 5 &&
      hasMoreCoupons(currentPage, limit, total)) {
    currentPage++;

    loadCoupons(currentPage, limit, categoryKey, categoryValue);
  }
}, {
  passive: true
});

const searchingInput = document.querySelector('.header-search-input');

searchingInput.addEventListener('change', handleChanging, {passive: true});

function handleChanging() {
  categoryKey = document.getElementById('select-category').value;
  categoryValue = searchingInput.value;
  document.querySelector('.header-search-input')

  currentPage = 1;
  removeCoupons(document.querySelectorAll('.coupon'))
  loadCoupons(currentPage, limit, categoryKey, categoryValue);
}

loadCoupons(currentPage, limit)
