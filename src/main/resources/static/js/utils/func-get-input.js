export function getInputExpenditure() {
    return {
        name: document.getElementById('txt-name-expenditure').value,
        size: document.getElementById('txt-size-expenditure').value,
        width: document.getElementById('txt-width-expenditure').value,
        value: parseFloat(document.getElementById('num-value-expenditure').value),
        observation: document.getElementById('txt-observation-expenditure').value,
        amount: parseInt(document.getElementById('num-quantity-expenditure').value, 10),
    };
}

export function getSelectedExpenditureType() {
    const expenseTypeElements = document.getElementsByName('expenditure-type');
    for (const element of expenseTypeElements) {
        if (element.checked) {
            return element.value;
        }
    }
    return null;
}

export function getInputSale() {
    return {
        product: {
            name: document.getElementById('txt-name-product').value,
            size: document.getElementById('txt-size-product').value,
            width: document.getElementById('txt-width-product').value
        },
        value: parseFloat(document.getElementById('num-value-sale').value),
        amount: -parseInt(document.getElementById('num-quantity-sale').value, 10),
        observation: document.getElementById('txt-observation').value
    };
}

export function getInputItem(idName, idSize, idWidth) {
    return {
        name: document.getElementById(idName).value,
        size: document.getElementById(idSize).value,
        width: document.getElementById(idWidth).value,
    };
}

export function getInputAddQuantityItem(idName, idSize, idWidth, idAmount) {
    return {
        name: document.getElementById(idName).value,
        size: document.getElementById(idSize).value,
        width: document.getElementById(idWidth).value,
        amount: parseInt(document.getElementById(idAmount).value, 10)
    };
}

export function getInputRemoveQuantityItem(idName, idSize, idWidth, idAmount) {
    return {
        name: document.getElementById(idName).value,
        size: document.getElementById(idSize).value,
        width: document.getElementById(idWidth).value,
        amount: -parseInt(document.getElementById(idAmount).value, 10)
    };
}

export function isInputValid({ name, size, width, value, amount }, isValueRequired = false, isQuantityAmountRequired = false) {
    let errorMessages = [];

    // Função para validar e sanitizar cada campo
    function validateAndSanitize(field, fieldName) {
        let result = sanitizeAndValidateString(field);
        if (!result.isValid) {
            errorMessages.push(`O campo ${fieldName} precisar ser corrigido para: ${result.sanitized}`);
        }
        return result.sanitized;
    }

    let sanitizedInputs = {
        name: validateAndSanitize(name, 'Nome'),
        size: validateAndSanitize(size, 'Tamanho'),
        width: validateAndSanitize(width, 'Largura'),
        value: isValueRequired ? validateAndSanitize(value, 'Valor') : value,
        amount: isQuantityAmountRequired ? validateAndSanitize(amount, 'Quantidade') : amount
    };

    if (errorMessages.length > 0) {
        alert(errorMessages.join('\n'));
        return false;
    }

    if (name === '' || size === '' || width === '' || (isValueRequired && (value === '' || isNaN(value) || value <= 0)) || (isQuantityAmountRequired && (amount === '' || isNaN(amount) || amount <= 0))) {
        alert("Preencha todos os campos corretamente!");
        return false;
    }
    return true;
}

function sanitizeAndValidateString(input) {
    let sanitizedInput = input.toString();
    let isValid = true;

    // Check for spaces and invalid characters
    if (/\s/.test(sanitizedInput) || /\W/.test(sanitizedInput)) {
        isValid = false;
        // Replace spaces with underscores
        sanitizedInput = sanitizedInput.replace(/\s+/g, '_');
        // Remove non-alphanumeric characters except underscores
        sanitizedInput = sanitizedInput.replace(/[^\w_]/g, '');
    }

    // Replace single quotes with escaped single quotes to prevent SQL injection
    sanitizedInput = sanitizedInput.replace(/'/g, "''");

    return { sanitized: sanitizedInput, isValid: isValid };
}
