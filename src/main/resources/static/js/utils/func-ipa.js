export function sendPostRequest(endpoint, data, resultElementId) {
    const token = sessionStorage.getItem('token'); // Recupera o token da sessionStorage

    fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` // Adiciona o token no cabeçalho Authorization
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
    const token = sessionStorage.getItem('token'); // Recupera o token da sessionStorage

    fetch(endpoint, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}` // Adiciona o token no cabeçalho Authorization
        }
    })
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

export async function confirmToken() {
    const token = sessionStorage.getItem('token');

    if (!token) {
        window.location.href = '/login';
    }

    try {
        const response = await fetch('/auth/token-is-valid', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({token: token})
        });
        if (response.ok) {
            const isValid = await  response.json();
            if (!isValid) {
                window.location.href = '/login';
            }
        } else {
            window.location.href = '/login';
        }
    } catch (error) {
        console.error('Erro ao verificar o token:', error);
        window.location.href = '/login';
    }
}
