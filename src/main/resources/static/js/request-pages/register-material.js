import {
    sendPostRequest,
    sendGetRequest,
} from '../utils/func-ipa.js';

import {
    formatProductName
} from '../utils/func-get-input.js';

function stringListStockMaterial() {
    sendGetRequest('/ipa-material/string-list-stock', 'result-string-list-stock-material');
}

function createMaterial() {
    document.getElementById("result-create-material").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja criar um novo material?")) {
        const material = {
            name: formatProductName(document.getElementById("txt-name-material").value),
            width: document.getElementById("txt-width-material").value,
            token: sessionStorage.getItem('token')
        }
        if (!material.name || !material.width) {
            alert("Todos os campos do material são obrigatórios.");
            return;
        }
        sendPostRequest('/ipa-material/create', material, 'result-create-material');
    }
}

function stringListMovementsOfMaterialSend() {
    const material = {
        name: formatProductName(document.getElementById("txt-name-material").value),
        width: document.getElementById("txt-width-material").value
    }
    if (!material.name || !material.width) {
        alert("Todos os campos do material são obrigatórios.");
        return;
    }
    sendPostRequest('/ipa-material/string-list-movements-of-material-send', material, 'result-string-list-movements-of-material-send');
}

function removeQuantityMaterial() {
    document.getElementById("result-append-quantity-material").innerHTML = "";
    if (window.confirm("Você tem certeza que deseja remover a quantidade do material?")) {
        const movementsRequest = {
            name: formatProductName(document.getElementById("txt-name-material").value),
            width: document.getElementById("txt-width-material").value,
            quantity: document.getElementById("num-remove-quantity-material").value,
            token: sessionStorage.getItem('token')
        };
        if (!movementsRequest.name || !movementsRequest.width || !movementsRequest.quantity) {
            alert("Todos os campos do material são obrigatórios.");
            return;
        }
        const quantity = parseFloat(movementsRequest.quantity);
        if (isNaN(quantity) || quantity <= 0) {
            alert("Digite valores positivos.");
            return;
        }
        sendPostRequest('/ipa-material/remove-quantity', movementsRequest, 'result-append-quantity-material');
    }
}

function stringListAllMaterialMovements() {
    sendGetRequest("/ipa-material/string-list-all-movements", "result-string-list-all-material-movements");
}

window.stringListStockMaterial = stringListStockMaterial;
window.createMaterial = createMaterial;
window.stringListMovementsOfMaterialSend = stringListMovementsOfMaterialSend;
window.removeQuantityMaterial = removeQuantityMaterial;
window.stringListAllMaterialMovements = stringListAllMaterialMovements;
