import {
    isInputValid,
    getInputItem,
    getInputRemoveQuantityItem,
} from '../utils/func-get-input.js';

import {
    sendPostRequest,
    sendGetRequest,
} from '../utils/func-ipa.js';

function stringListStockMaterial(){
    sendGetRequest('/ipa-material/string-list-stock', 'result-string-list-stock-material');
}

function createMaterial() {
    const material = getInputItem("txt-name-material", "txt-size-material", "txt-width-material");
    if (isInputValid(material)) {
        sendPostRequest('/ipa-material/create', material, 'result-create-material');
    }
}

function stringListMovementsOfMaterialSend() {
    const material = getInputItem("txt-name-material", "txt-size-material", "txt-width-material");
    if (isInputValid(material)) {
        sendPostRequest('/ipa-material/string-list-movements-of-material-send', material, 'result-string-list-movements-of-material-send');
    }
}

function appendQuantityMaterial() {
    const movementsRequest = getInputRemoveQuantityItem("txt-name-material", "txt-size-material", "txt-width-material", "num-remove-quantity-material");
    if (isInputValid(movementsRequest, false, true)) {
        sendPostRequest('/ipa-material/append-quantity', movementsRequest, 'result-append-quantity-material');
    }
}

function stringListAllMaterialMovements(){
    sendGetRequest("/ipa-material/string-list-all-movements", "result-string-list-all-material-movements")
}

window.stringListStockMaterial = stringListStockMaterial;
window.createMaterial = createMaterial;
window.stringListMovementsOfMaterialSend = stringListMovementsOfMaterialSend;
window.appendQuantityMaterial = appendQuantityMaterial;
window.stringListAllMaterialMovements = stringListAllMaterialMovements;
