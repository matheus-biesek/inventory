import {
    isInputValid,
    getInputItem
} from '../utils/func-get-input.js';

import {
    sendPostRequest,
    sendGetRequest,
} from '../utils/func-ipa.js';

function isValidInputMaterial(movementsRequest) {
    if (!movementsRequest.name || !movementsRequest.size || !movementsRequest.width) {
        alert("Todos os campos do material são obrigatórios.");
        return false;
    }
    if (isNaN(movementsRequest.amount) || movementsRequest.amount <= 0) {
        alert("A quantidade deve ser um número positivo.");
        return false;
    }
    return true;
}

function stringListStockMaterial(){
    sendGetRequest('/ipa-material/string-list-stock', 'result-string-list-stock-material');
}

function createMaterial() {
    document.getElementById("result-create-material").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja criar um novo material?")) {
        const material = getInputItem("txt-name-material", "txt-size-material", "txt-width-material");
        if (isInputValid(material)) {
            sendPostRequest('/ipa-material/create', material, 'result-create-material');
        }
    }
}

function stringListMovementsOfMaterialSend() {
    const material = getInputItem("txt-name-material", "txt-size-material", "txt-width-material");
    if (isInputValid(material)) {
        sendPostRequest('/ipa-material/string-list-movements-of-material-send', material, 'result-string-list-movements-of-material-send');
    }
}
//Remove quantity
function appendQuantityMaterial() {
    document.getElementById("result-append-quantity-material").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja remover a quantidade do material?")) {
        const movementsRequest = {
            name: document.getElementById("txt-name-material").value,
            size: document.getElementById("txt-size-material").value,
            width: document.getElementById("txt-width-material").value,
            amount: parseFloat(document.getElementById("num-remove-quantity-material").value.replace(',', '.'))
        };
        console.log(movementsRequest.amount);
        if (isValidInputMaterial(movementsRequest)) {
            sendPostRequest('/ipa-material/append-quantity', movementsRequest, 'result-append-quantity-material');
        }
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
