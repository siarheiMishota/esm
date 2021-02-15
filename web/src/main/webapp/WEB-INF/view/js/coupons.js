// const domain = 'localhost:8080'
// const mainUrl = 'giftCertificates'
const domain = 'https://api.javascripttutorial.net'
const mainUrl = 'v1/quotes'
let category = null;
let searchValue = null;

const showCoupons = (coupons) => {
  coupons.forEach(coupon => {
    const couponEl = document.createElement('div');
    couponEl.classList.add('coupon');

    couponEl.innerHTML = `
        <img class="coupon-image" src="svg/collections.svg" alt="photo of coupon">
              <div class="information">
                <div class="one-block">
                  <div class="coupon-name">
                    <label>
                      ${coupon.quote}
                    </label>
                  </div>
                  <div class=favorite>
                    <img src="svg/favorite.svg" alt="photo of favorite">
                  </div>
                </div>
                <div class="one-block">
                  <div class="brief-description">
                    <label>
                      ${coupon.author}
                    </label>
                  </div>
                  <div class=duration-delivery>
                    <label>
                      Express in ${coupon.id} days
                    </label>
                  </div>
                </div>
                <div class="third-block">
                  <div class="price">
                    <label>
                      $${coupon.id}
                    </label>
                  </div>
                  <div class=add-to-cart>
                    <input class="submit-add-to-cart" type="submit" value="Add to cart">
                  </div>
                </div>
              </div>`;
    couponsEl.appendChild(couponEl);
  });
};

const removeCoupons = (coupons) => {
  coupons.forEach(coupon => coupon.remove())
}

const hasMoreCoupons = (page, limit, total) => {
  const startIndex = (page - 1) * limit + 1;
  return total === 0 || startIndex < total;
}

const loadCoupons = async (page, limit, categoryKey, categoryValue) => {
  showLoader();
  setTimeout(async () => {
    try {
      if (hasMoreCoupons(page, limit, total)) {
        const response = await getCoupons(page, limit,categoryKey,categoryValue);
        showCoupons(response.data);
        total = response.total;
      }
    } catch (error) {
      console.log(error.message);
    } finally {
      hideLoader();
    }
  }, 1000);

};

const getCoupons = async (page, limit, keyCategory, valueCategory) => {
  let searchCategory = ''
  if (''!==keyCategory&&''!==valueCategory) {
    searchCategory = `${keyCategory}=${valueCategory}&`
  }
  const giftCertificatesUrl = `${domain}/${mainUrl}/?${searchCategory}page=${page}&limit=${limit}`
  const response = await fetch(giftCertificatesUrl)

  if (!response.ok) {
    throw  new Error('An error occurred: ${response.status}');
  }
  return await response.json();
}