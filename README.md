Gerenciador de Pedidos

Sistema web desenvolvido com Spring Boot para gerenciamento de pedidos, produtos e usuários, com autenticação via JWT e geração de relatórios administrativos.



🚀 Tecnologias utilizadas

\- Java 17

\- Spring Boot 3.2.5

\- Spring Security + JWT

\- Spring Data JPA + Hibernate

\- MySQL

\- Maven

\- JUnit 5 + Mockito

\- MockMvc (para testes de controller)



Autenticação

A aplicação utiliza JWT (JSON Web Token) token válido no header:

Authorization: Bearer <gerenciador>



Endpoints públicos:

\- /auth/login

\- /auth/register

\- /relatorios/\*\* (liberado via configuração de segurança)



📊 Relatórios disponíveis

\- GET /relatorios/top-clientes → Lista os clientes com mais pedidos

\- GET /relatorios/ticket-medio → Retorna o ticket médio por cliente

\- GET /relatorios/faturamento-mensal → Retorna o faturamento total do mês



🛠️ Como rodar o projeto

\- Clone o repositório:



git clone https://github.com/Danielfilho90/GerenciadorDePedidos.git

cd gerenciador-de-pedidos



\- Configure o banco de dados no application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/gerenciador

spring.datasource.username=root

spring.datasource.password=Daniel#61



\- Compile e rode:

mvn clean install

mvn spring-boot:run

\- Acesse:

http://localhost:8080







🧪 Testes usei o junit

Execute os testes com:

mvn test





Cobertura de testes para:

\- Serviços (PedidoService, ProdutoService)

\- Controllers (RelatorioController)

\- Autenticação (JwtUtil, JwtFilter)







