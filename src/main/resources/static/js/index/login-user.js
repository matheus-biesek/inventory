function saveTokenToLocalStorage(token) {
    localStorage.setItem('token', token);
}

const loginForm = document.querySelector('#login-form');

loginForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = new FormData(loginForm);
    const username = formData.get('username');
    const password = formData.get('password');

    try {
        const response = await fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            throw new Error('Credenciais inválidas');
        }

        const data = await response.json();
        const { token } = data;

        saveTokenToLocalStorage(token);

        // Redirecionar para página privada ou atualizar conteúdo após o login
        loadPrivatePage();

    } catch (error) {
        console.error('Erro ao fazer login:', error);
    }
});

// Função para adicionar o token ao cabeçalho das requisições
function addAuthTokenToHeaders(headers) {
    const token = localStorage.getItem('token');
    if (token) {
        headers.append('Authorization', `Bearer ${token}`);
    }
}

// Função para requisições autenticadas
async function fetchWithAuth(url, options = {}) {
    options.headers = options.headers || new Headers();
    addAuthTokenToHeaders(options.headers);
    const response = await fetch(url, options);
    return response;
}

// Exemplo de uso: Fetch com token para carregar a página privada
async function loadPrivatePage() {
    try {
        const response = await fetchWithAuth('http://localhost:8080/register-material');

        if (!response.ok) {
            throw new Error(`Erro ao carregar página privada: ${response.status} - ${response.statusText}`);
        }

        const contentType = response.headers.get('content-type');
        if (!contentType || !contentType.includes('application/json')) {
            throw new Error('Resposta não contém JSON válido');
        }

        const data = await response.json();
        console.log('Dados da página privada:', data);

        // Aqui você pode atualizar a página com os dados recebidos
        // updatePrivateContent(data);

    } catch (error) {
        console.error('Erro ao carregar a página privada:', error);
    }
}

