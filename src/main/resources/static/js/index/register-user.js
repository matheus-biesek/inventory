async function registerUser() {
    const username = document.getElementById('txt-username').value;
    const password = document.getElementById('txt-password').value;
    const confirmPassword = document.getElementById('txt-confirm-password').value;

    if (password !== confirmPassword) {
        document.getElementById('register-result').textContent = 'As senhas não coincidem';
        return;
    }

    const response = await fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) {
        const data = await response.json();
        sessionStorage.setItem('token', data.token);

        alert('Cadastro feito com sucesso!');
        window.location.href = '/register-product';
    } else {
        document.getElementById('register-result').textContent = 'Falha no registro';
    }
}
