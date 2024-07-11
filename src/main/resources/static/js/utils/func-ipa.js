export function sendPostRequest(endpoint, data, resultElementId) {
    fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(response => response.text())
        .then(result => {
            document.getElementById(resultElementId).innerHTML = formatResult(result);
        })
        .catch(error => console.error('Erro:', error));
}

export function sendGetRequest(endpoint, resultElementId) {
    fetch(endpoint)
        .then(response => response.text())
        .then(result => {
            document.getElementById(resultElementId).innerHTML = formatResult(result);
        })
        .catch(error => console.error('Erro:', error));
}

export function formatResult(result) {
    const lines = result.split('\n');
    const formattedResult = lines.map((line, index) => {
        if (index === 0) {
            return `<p>${line}</p>`;
        }
        return `<p>${line.trim()}</p>`;
    }).join('');
    return formattedResult;
}