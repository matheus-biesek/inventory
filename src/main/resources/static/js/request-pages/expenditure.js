import {
    sendPostRequest,
    sendGetRequest
} from '../utils/func-ipa.js';

function getSelectedExpenditureType() {
    const expenseTypeElements = document.getElementsByName('expenditure-type');
    for (const element of expenseTypeElements) {
        if (element.checked) {
            return element.value;
        }
    }
    return null;
}

function stringListStockExpenditure() {
    sendGetRequest("/ipa-expenditure/string-list-stock", 'result-string-list-stock-expenditure');
}

function createExpenditure() {
    document.getElementById("result-create-expenditure").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja criar uma nova despesa?")) {
        const expenditure = {
            name: document.getElementById('txt-name-expenditure').value.replace(/\s+/g, '_'),
            width: document.getElementById('txt-width-expenditure').value,
            value: document.getElementById('num-value-expenditure').value,
            observation: document.getElementById('txt-observation-expenditure').value,
            size: document.getElementById('num-quantity-expenditure').value
        };
        if (!expenditure.name  || !expenditure.width || !expenditure.value || !expenditure.size) {
            alert("Todos os campos do produto são obrigatórios.");
            return;
        }
        const value = parseFloat(expenditure.value);
        const size = parseFloat(expenditure.size);
        if (isNaN(value) || value <= 0 || isNaN(size) || size <= 0) {
            alert("O valor e a quantidade devem ser maiores que zero.");
            return;
        }
        const type = getSelectedExpenditureType();
        if (type === 'capex') {
            sendPostRequest('/ipa-expenditure/create-capex', expenditure, 'result-create-expenditure');
        } else if (type === 'opex') {
            sendPostRequest('/ipa-expenditure/create-opex', expenditure, 'result-create-expenditure');
        } else {
            alert("Selecione o tipo de despesa!");
        }
    }
}

function stringListOfExpenditureSend() {
    const expenditure = {
        name: document.getElementById("txt-name-expenditure").value.replace(/\s+/g, '_'),
        width: document.getElementById("txt-width-expenditure").value,
    }
    if (!expenditure.name  || !expenditure.width) {
        alert("Todos os campos do produto são obrigatórios.");
        return;
    }
    sendPostRequest("/ipa-expenditure/string-list-of-expenditure-send", expenditure, 'result-string-list-of-expenditure-send');
}

function stringListAllExpenditure() {
    sendGetRequest("/ipa-expenditure/string-list-all", 'result-string-list-all-expenditure');
}

window.stringListStockExpenditure = stringListStockExpenditure;
window.createExpenditure = createExpenditure;
window.stringListOfExpenditureSend = stringListOfExpenditureSend;
window.stringListAllExpenditure = stringListAllExpenditure;
