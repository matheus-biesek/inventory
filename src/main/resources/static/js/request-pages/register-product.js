import {
    isInputValid,
    getInputItem,
    getInputAddQuantityItem,
} from '../utils/func-get-input.js';

import {
    sendPostRequest,
    sendGetRequest,
} from '../utils/func-ipa.js';

function stringListStockProduct(){
    sendGetRequest('/ipa-product/string-list-stock', 'result-string-list-stock-product');
}

function createProduct() {
    document.getElementById("result-create-product").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja adicionar um novo produto no banco de dados?")) {
        const product = getInputItem("txt-name-product", "txt-size-product", "txt-width-product");
        if (isInputValid(product)) {
            sendPostRequest('/ipa-product/create', product, 'result-create-product');
        }
    }
}

function stringListMovementsOfProductSend() {
    const product = getInputItem("txt-name-product", "txt-size-product", "txt-width-product");
    if (isInputValid(product)) {
        sendPostRequest('/ipa-product/string-list-movements-of-product-send', product, 'result-string-list-movements-of-product-send');
    }
}

function addQuantityProduct() {
    document.getElementById("result-add-quantity-product").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja adicionar uma nova quantidade no produto?")) {
        const moveRequest = getInputAddQuantityItem("txt-name-product", "txt-size-product", "txt-width-product", "num-add-quantity-product");
        if (isInputValid(moveRequest, false, true)) {
            sendPostRequest('/ipa-product/append-quantity', moveRequest, 'result-add-quantity-product');
        }
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