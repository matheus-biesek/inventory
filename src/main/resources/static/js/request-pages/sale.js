import {
    isInputValid,
    getInputItem
} from '../utils/func-get-input.js';

import {
    sendPostRequest,
    sendGetRequest
} from '../utils/func-ipa.js';

function isInputValidSale(sale) {
    if (!sale.product.name || !sale.product.size || !sale.product.width) {
        alert("Todos os campos do produto são obrigatórios.");
        return false;
    }
    if (isNaN(sale.value) || sale.value <= 0) {
        alert("O valor da venda deve ser um número positivo.");
        return false;
    }
    if (isNaN(sale.amount) || sale.amount >= 0) {
        alert("Mude o sinal da quantidade.");
        return false;
    }
    return true;
}

function createSale() {
    document.getElementById("result-create-sale").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja cria uma nova venda?")) {
        const sale = {
            product: {
                name: document.getElementById('txt-name-product').value,
                size: document.getElementById('txt-size-product').value,
                width: document.getElementById('txt-width-product').value
            },
            value: parseFloat(document.getElementById('num-value-sale').value),
            amount: -parseFloat(document.getElementById('num-quantity-sale').value.replace(',', '.')),
            observation: document.getElementById('txt-observation').value
        };
        if (isInputValidSale(sale)) {
            sendPostRequest('/ipa-sale/create', sale, 'result-create-sale');
        }
    }
}

function stringListStockSale() {
    sendGetRequest("/ipa-sale/string-list-stock", 'result-string-list-stock-sale');
}

function stringListSaleOfProductSend() {
    const product = getInputItem("txt-name-product", "txt-size-product", "txt-width-product");
    if(isInputValid(product)){
        sendPostRequest("/ipa-sale/string-list-sale-of-product-send", product, "result-string-list-sale-of-product-send");
    }
}

function stringListAllSale() {
    sendGetRequest("/ipa-sale/string-list-all", "result-string-list-all-sale");
}

window.stringListStockSale = stringListStockSale;
window.createSale = createSale;
window.stringListSaleOfProductSend = stringListSaleOfProductSend;
window.stringListAllSale = stringListAllSale;
