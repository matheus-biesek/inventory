import {
    isInputValid,
    getInputItem,
} from '../utils/func-get-input.js';

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

function isValidValueAmount(expenditure) {
    return !(expenditure.value < 0 || expenditure.amount < 0);
}

function stringListStockExpenditure() {
    sendGetRequest("/ipa-expenditure/string-list-stock", 'result-string-list-stock-expenditure');
}

function createExpenditure() {
    document.getElementById("result-create-expenditure").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja criar uma nova despesa?")) {
        const expenditure = {
            name: document.getElementById('txt-name-expenditure').value,
            size: document.getElementById('txt-size-expenditure').value,
            width: document.getElementById('txt-width-expenditure').value,
            value: parseFloat(document.getElementById('num-value-expenditure').value.replace(',', '.')),
            observation: document.getElementById('txt-observation-expenditure').value,
            amount: parseFloat(document.getElementById('num-quantity-expenditure').value.replace(',', '.'))
        };
        if (isValidValueAmount(expenditure) && isInputValid(expenditure)) {
            const type = getSelectedExpenditureType();
            if (type === 'capex') {
                sendPostRequest('/ipa-expenditure/create-capex', expenditure, 'result-create-expenditure');
            } else if (type === 'opex') {
                sendPostRequest('/ipa-expenditure/create-opex', expenditure, 'result-create-expenditure');
            } else {
                alert("Selecione o tipo de despesa!");
            }
        } else {
            alert("Os valores inseridos são inválidos. Certifique-se de que não há valores negativos.");
        }
    }
}

function stringListOfExpenditureSend() {
    const expenditure = getInputItem("txt-name-expenditure", "txt-size-expenditure", "txt-width-expenditure");
    if (isInputValid(expenditure)) {
        sendPostRequest("/ipa-expenditure/string-list-of-expenditure-send", expenditure, 'result-string-list-of-expenditure-send');
    }
}

function stringListAllExpenditure() {
    sendGetRequest("/ipa-expenditure/string-list-all", 'result-string-list-all-expenditure');
}

window.stringListStockExpenditure = stringListStockExpenditure;
window.createExpenditure = createExpenditure;
window.stringListOfExpenditureSend = stringListOfExpenditureSend;
window.stringListAllExpenditure = stringListAllExpenditure;
