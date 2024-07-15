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
        amount: parseFloat(document.getElementById(idAmount).value.replace(',', '.'))
    };
}

export function isInputValid({ name, size, width, value, amount }, isValueRequired = false, isQuantityAmountRequired = false) {
    let errorMessages = [];

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