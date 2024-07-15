import {
    getInputSale,
    isInputValid,
    getInputItem
} from '../utils/func-get-input.js';

import {
    sendPostRequest,
    sendGetRequest,
    confirmToken,
} from '../utils/func-ipa.js';

confirmToken();

function stringListStockSale() {
    sendGetRequest("/ipa-sale/string-list-stock", 'result-string-list-stock-sale');
}

function createSale() {
    const sale = getInputSale()
    if (isInputValid(sale, true, true)) {
        sendPostRequest('/ipa-sale/create', sale, 'result-create-sale');
    }
}


function stringListSaleOfProductSend(){
    const product = getInputItem("txt-name-product", "txt-size-product", "txt-width-product");
    if (isInputValid(product)) {
        sendPostRequest("/ipa-sale/string-list-sale-of-product-send", product, "result-string-list-sale-of-product-send");
    }
}

function stringListAllSale(){
    sendGetRequest("/ipa-sale/string-list-all", "result-string-list-all-sale")
}

window.stringListStockSale =stringListStockSale;
window.createSale = createSale;
window.stringListSaleOfProductSend = stringListSaleOfProductSend;
window.stringListAllSale = stringListAllSale;