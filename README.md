# Neo API

API REST desenvolvida em **Java 21** com **Spring Boot**, para cadastro e gerenciamento de usuários e clientes, com autenticação JWT e documentação via Swagger.

## 🚀 Tecnologias

* Java 21
* Spring Boot 3.5.5
* Spring Web
* Spring Data JPA
* H2 Database (dev/test)
* Lombok
* MapStruct
* AuthTokenService (JWT)
* Springdoc OpenAPI (Swagger UI)
* Mockito / JUnit 5

## 📦 Instalação

Clone o repositório e compile com Maven:

```bash
git clone https://github.com/gabrieljn/neo-api.git
cd neo-api
mvn clean install
```

## ⚙️ Configuração

No arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:neo
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

## ▶️ Executando

### Dev

```bash
mvn spring-boot:run
```

A API estará em: `http://localhost:8080`

### Swagger UI

* `http://localhost:8080/swagger-ui/index.html`
* `http://localhost:8080/v3/api-docs`

Deploy (Koyeb):
👉 [Swagger Online](https://moral-maressa-gabrielvs-7a9e9b51.koyeb.app/swagger-ui/index.html)

## 📚 Endpoints

### 🔑 Login

* **POST /login** → autentica usuário e retorna token JWT

  * Body:

    ```json
    {
      "usuario": "gabriel123",
      "senha": "123456"
    }
    ```

### 👤 Usuários

* **POST /usuarios** → cadastra novo usuário
* **GET /usuarios** → lista todos os usuários
* **PUT /usuarios/{idUsuario}** → atualiza usuário
* **DELETE /usuarios/{idUsuario}** → deleta usuário
* **PUT /usuarios/{idUsuario}/senha** → atualiza senha do usuário

  * Body:

    ```json
    {
      "senhaAtual": "123456",
      "novaSenha": "654321"
    }
    ```

### 🧾 Clientes

* **POST /clientes** → cadastra cliente

  * Body:

    ```json
    {
      "nomeCliente": "Gabriel Alves",
      "cpf": "123.456.789-00",
      "dataNascimento": "1990-05-01",
      "dataCadastro": "2025-03-09"
    }
    ```
* **GET /clientes** → lista todos os clientes
* **GET /clientes/cpf/{cpf}** → busca cliente por CPF
* **GET /clientes/nome** → busca clientes por nome
* **PUT /clientes/{idCliente}** → atualiza cliente
* **DELETE /clientes/{idCliente}** → deleta cliente

## 📂 Estrutura

```
neo-api/
├── src/main/java/com/neo/
│   ├── controllers/
│   ├── dtos/
│   ├── models/
│   ├── repositories/
│   ├── services/
│   └── NeoApiApplication.java
├── src/test/java/com/neo/
├── pom.xml
└── README.md
```

Projeto sob licença MIT - veja [LICENSE](LICENSE).
