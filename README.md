Tecnologias Utilizadas

Backend:
Spring Boot,
Spring Web,
Spring Data JPA,
Spring Security,
JWT (JSON Web Tokens),
PostgreSQL,
Lombok.

Frontend:
HTML,
CSS,
JavaScript.

Descrição do Projeto

A aplicação permite o gerenciamento eficiente de estoque, incluindo adição, remoção e atualização de itens. Além disso, ela oferece uma análise gráfica dos dados de estoque para auxiliar na tomada de decisões estratégicas.
Estrutura do Projeto

Backend: Implementado em Java utilizando o Spring Boot, com segurança gerenciada pelo Spring Security e autenticação JWT.

Frontend: Desenvolvido com HTML, CSS e JavaScript para uma interface de usuário responsiva e interativa.

Banco de Dados: Utiliza PostgreSQL para armazenamento e gerenciamento de dados.

Funcionalidades

Gerenciamento de Estoque:
Adicionar novos itens ao estoque,
Atualizar informações de itens existentes,
Remover itens do estoque

Análise Gráfica:
Visualização de dados de estoque em gráficos.

Autenticação e Autorização:
Utilização de JWT para autenticação de usuários,
Controle de acesso a funcionalidades através do Spring Security

Problemas Conhecidos

Atualmente, há uma vulnerabilidade identificada no sistema:

Problema: O backend não consegue coletar o token do usuário no método recoverToken da classe TokenService.

Impacto: As páginas HTML estão públicas, mas a um script da página que verifica se o token é válido. No entanto, um atacante pode desativar esse script e acessar a página. 

Apesar disso, ele não conseguirá realizar requisições privadas, pois estas dependem de um token válido.

Contribuição

Contribuições são bem-vindas! Se você encontrar problemas ou tiver sugestões de melhorias, por favor, abra uma issue ou envie um pull request.

Licença

Este projeto é de minha autoria e todos os direitos são reservados a mim. Para mais detalhes, veja o arquivo LICENSE.
