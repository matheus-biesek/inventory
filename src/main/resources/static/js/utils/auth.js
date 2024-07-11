export function checkAuth() {
    const token = sessionStorage.getItem('token');
    console.log('Token recuperado:', token); // Log para depuração

    if (!token) {
        // Se não houver token na sessionStorage, redirecione para a página de login
        window.location.href = '/login-user';
        return;
    }

    // Faça uma requisição de verificação ao servidor, se necessário
    fetch('http://localhost:8080/auth/verify-token', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Falha na verificação do token');
            }
            console.log('Token verificado com sucesso'); // Log para depuração
        })
        .catch(error => {
            console.error('Erro ao verificar token:', error.message);
            window.location.href = '/login-user';
        });
}
