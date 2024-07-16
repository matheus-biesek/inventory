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
        const product = {
            name: document.getElementById("txt-name-product").value.replace(/\s+/g, '_'),
            size: document.getElementById("txt-size-product").value,
            width: document.getElementById("txt-width-product").value,
            token: sessionStorage.getItem('token'),
        }
        if (!product.name || !product.size || !product.width) {
            alert("Todos os campos do material são obrigatórios.");
            return;
        }
        sendPostRequest('/ipa-product/create', product, 'result-create-product');
    }
}

function stringListMovementsOfProductSend() {
    const product = {
        name: document.getElementById("txt-name-product").value.replace(/\s+/g, '_'),
        size: document.getElementById("txt-size-product").value,
        width: document.getElementById("txt-width-product").value,
    }
    if (!product.name || !product.size || !product.width) {
        alert("Todos os campos do material são obrigatórios.");
        return;
    }
    sendPostRequest('/ipa-product/string-list-movements-of-product-send', product, 'result-string-list-movements-of-product-send');
}

function addQuantityProduct() {
    document.getElementById("result-add-quantity-product").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja adicionar uma nova quantidade no produto?")) {
        const moveRequest = {
            name: document.getElementById("txt-name-product").value.replace(/\s+/g, '_'),
            width: document.getElementById("txt-width-product").value,
            size: document.getElementById("txt-size-product").value,
            amount: parseInt(document.getElementById("num-add-quantity-product").value, 10),
            token: sessionStorage.getItem('token'),
        }
        if (!moveRequest.name || !moveRequest.size || !moveRequest.width || !moveRequest.amount) {
            alert("Todos os campos do material são obrigatórios.");
            return;
        }
        const amount = moveRequest.amount;
        if (isNaN(amount) || amount <= 0) {
            alert("Digite valores positivos.");
            return;
        }
        sendPostRequest('/ipa-product/add-quantity', moveRequest, 'result-add-quantity-product');
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