import {
    sendPostRequest,
    sendGetRequest
} from '../utils/func-ipa.js';

import {
    formatProductName
} from '../utils/func-get-input.js'; // Adicione a importação da função

function createSale() {
    document.getElementById("result-create-sale").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja criar uma nova venda?")) {
        const sale = {
            name: formatProductName(document.getElementById('txt-name-product').value), // Usar a função formatProductName
            size: document.getElementById('txt-size-product').value,
            width: document.getElementById('txt-width-product').value,
            value: document.getElementById('num-value-sale').value,
            amount: parseInt(document.getElementById('num-quantity-sale').value, 10),
            observation: document.getElementById('txt-observation').value,
            token: sessionStorage.getItem('token')
        };
        if (!sale.name || !sale.size || !sale.width || !sale.amount || !sale.value) {
            alert("Todos os campos do produto são obrigatórios.");
            return;
        }
        const value = parseFloat(sale.value);
        const amount = sale.amount;
        if (isNaN(value) || value <= 0 || isNaN(amount) || amount <= 0) {
            alert("Digite valores positivos.");
            return;
        }
        sendPostRequest('/ipa-sale/create', sale, 'result-create-sale');
    }
}

function stringListStockSale() {
    sendGetRequest("/ipa-sale/string-list-stock", 'result-string-list-stock-sale');
}

function stringListSaleOfProductSend() {
    const product = {
        name: formatProductName(document.getElementById("txt-name-product").value), // Usar a função formatProductName
        width: document.getElementById("txt-width-product").value,
        size: document.getElementById("txt-size-product").value,
    }
    if (!product.name || !product.size || !product.width) {
        alert("Todos os campos do material são obrigatórios.");
        return;
    }
    sendPostRequest("/ipa-sale/string-list-sale-of-product-send", product, "result-string-list-sale-of-product-send");
}

function stringListAllSale() {
    sendGetRequest("/ipa-sale/string-list-all", "result-string-list-all-sale");
}

window.stringListStockSale = stringListStockSale;
window.createSale = createSale;
window.stringListSaleOfProductSend = stringListSaleOfProductSend;
window.stringListAllSale = stringListAllSale;
