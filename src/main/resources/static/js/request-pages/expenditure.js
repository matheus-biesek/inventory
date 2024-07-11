import {
    getInputExpenditure,
    getSelectedExpenditureType,
    isInputValid,
    getInputItem,
} from '../utils/func-get-input.js';

import {
    sendPostRequest,
    sendGetRequest,
} from '../utils/func-ipa.js';

//Functions in HTML
function stringListStockExpenditure() {
    sendGetRequest("/ipa-expenditure/string-list-stock", 'result-string-list-stock-expenditure');
}

function createExpenditure() {
    const expenditure = getInputExpenditure();
    if (isInputValid(expenditure,true, true)) {
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