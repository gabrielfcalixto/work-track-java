# Project Manager

Project Manager é um sistema de gerenciamento de projetos desenvolvido com Java 21, Spring Boot e PostgreSQL. Ele oferece funcionalidades para controle de projetos, tarefas, horas trabalhadas e gerenciamento de usuários, com autenticação e autorização via JWT.

## Tecnologias Utilizadas
- Java 21
- Spring Boot 3.4.2
- PostgreSQL
- Spring Data JPA
- Spring Security
- JWT (Json Web Token)
- Lombok
- Springdoc OpenAPI
- Maven

## Configuração do Ambiente

1. **Clone o Repositório:**
```bash
git clone https://github.com/seu-usuario/project-manager.git
cd project-manager
```

2. **Configure o Banco de Dados:**
Certifique-se de ter o PostgreSQL instalado e crie um banco de dados:
```sql
CREATE DATABASE project_manager;
```

3. **Configure o `application.properties` ou `application.yml`:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/project_manager
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.security.jwt.secret=secretoParaJWT
spring.security.jwt.expiration=86400000
```

## Como Executar

1. **Compile o Projeto:**
```bash
mvn clean install
```
2. **Execute a Aplicação:**
```bash
mvn spring-boot:run
```
A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)

## Documentação da API

A documentação interativa (Swagger) está disponível em:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Autenticação
Utilize o endpoint `/auth/login` para obter o token JWT:
```json
POST /auth/login
{
  "username": "admin",
  "password": "admin123"
}
```
O token JWT deve ser usado no cabeçalho `Authorization` para acessar os demais endpoints:
```http
Authorization: Bearer <seu_token_jwt>
```

## Testes

Para executar os testes automatizados:
```bash
mvn test
```

## Gerando Artefato

Caso queira gerar o arquivo `.jar` do projeto:
```bash
mvn package
```
O arquivo gerado ficará em `target/project-manager-0.0.1-SNAPSHOT.jar` e pode ser executado com:
```bash
java -jar target/project-manager-0.0.1-SNAPSHOT.jar
```

## Contribuindo
1. Fork o repositório.
2. Crie uma nova branch:
```bash
git checkout -b feature/nova-funcionalidade
```
3. Faça suas alterações e comite:
```bash
git commit -m "Adiciona nova funcionalidade X"
```
4. Envie para seu fork:
```bash
git push origin feature/nova-funcionalidade
```
5. Abra um Pull Request.

## Licença
Este projeto é licenciado sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---
Desenvolvido com por Gabriel.

