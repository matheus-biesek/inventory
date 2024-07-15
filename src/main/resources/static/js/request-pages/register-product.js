import {
    isInputValid,
    getInputItem,
    getInputAddQuantityItem,
} from '../utils/func-get-input.js';

import {
    sendPostRequest,
    sendGetRequest,
    confirmToken,
} from '../utils/func-ipa.js';

confirmToken();

function stringListStockProduct(){
    sendGetRequest('/ipa-product/string-list-stock', 'result-string-list-stock-product');
}

function createProduct() {
    const product = getInputItem("txt-name-product", "txt-size-product", "txt-width-product");
    if (isInputValid(product)) {
        sendPostRequest('/ipa-product/create', product, 'result-create-product');
    }
}

function stringListMovementsOfProductSend() {
    const product = getInputItem("txt-name-product", "txt-size-product", "txt-width-product");
    if (isInputValid(product)) {
        sendPostRequest('/ipa-product/string-list-movements-of-product-send', product, 'result-string-list-movements-of-product-send');
    }
}

function addQuantityProduct() {
    const moveRequest = getInputAddQuantityItem("txt-name-product", "txt-size-product", "txt-width-product", "num-add-quantity-product");
    if (isInputValid(moveRequest, false, true)) {
        sendPostRequest('/ipa-product/append-quantity', moveRequest, 'result-add-quantity-product');
    }
}

function stringListAllProductMovements(){
    sendGetRequest("/ipa-product/string-list-all-movements", "result-string-list-all-product-movements")
}

window.stringListStockProduct = stringListStockProduct;
window.createProduct =createProduct;
window.stringListMovementsOfProductSend = stringListMovementsOfProductSend;
window.addQuantityProduct = addQuantityProduct;
window.stringListAllProductMovements = stringListAllProductMovements;
