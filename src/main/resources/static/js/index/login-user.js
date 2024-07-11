async function loginUser() {
    const email = document.getElementById('txt-username').value;
    const password = document.getElementById('txt-password').value;

    const response = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    });

    if (response.ok) {
        const data = await response.json();
        sessionStorage.setItem('token', data.Token); // Certifique-se que é exatamente "Token"
        sessionStorage.setItem('username', data.name);
        alert('Login bem-sucedido!');
        window.location.href = '/register-material'; // Redireciona para uma página privada
    } else {
        document.getElementById('login-result').textContent = 'Falha no login';
    }
}
