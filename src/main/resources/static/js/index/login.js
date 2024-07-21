const loginForm = document.querySelector('#login-form');

loginForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = new FormData(loginForm);
    const username = formData.get('username');
    const password = formData.get('password');

    try {
        const response = await fetch('/auth/login', {
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
        sessionStorage.setItem('token', token);
        alert("Login efetuado com sucesso!");
        window.location.href = '/register-product';

    } catch (error) {
        console.error('Erro ao fazer login:', error);
        alert("Credenciais inválidas");
    }
});
