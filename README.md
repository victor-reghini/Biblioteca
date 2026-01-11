# Biblioteca

Sistema de gerenciamento de livros, usuários e empréstimos, desenvolvido em **Java 17** com **Spring Boot** e **PostgreSQL**.

---

## Visão Geral

A aplicação Biblioteca expõe uma **API REST** para:

- Cadastro e gerenciamento de **usuários**
- Cadastro e gerenciamento de **livros**
- Controle de **empréstimos de livros**

O projeto segue uma arquitetura em camadas (**Controller → Service → Repository**) e possui testes unitários e de controller com **JUnit 5**, **Mockito** e **MockMvc**.

---

## Requisitos

- **Java 17**
- **Maven**
- **PostgreSQL**

---

## Configuração do Banco de Dados

### Arquivo de configuração

```
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Criação das tabelas

Executar o script SQL:

```
src/main/resources/create_tables_database.sql
```

---

## Executando a Aplicação

```bash
mvn clean install
```

Executar a classe:

```
src/main/java/biblioteca/BibliotecaApplication.java
```

Aplicação disponível em:

```
http://localhost:8080
```

---

## Uso da API

### Usuários (`/user`)

#### Criar usuário

```bash
POST /user
```

```json
{
  "nome": "Victor Silva",
  "email": "victor@email.com",
  "dataCadastro": "2026-01-11T00:00:00",
  "telefone": 44999999999
}
```

**Erros possíveis**
- `400 BAD REQUEST` – Data de cadastro inválida (posterior à data atual)

---

#### Atualizar usuário

```bash
PUT /user
```

```json
{
  "id": 1,
  "nome": "Victor Silva"
}
```

---

#### Buscar usuário por ID

```bash
GET /user/{id}
```

**Erros possíveis**
- Retorno vazio quando usuário não encontrado

---

#### Listar usuários

```bash
GET /user/list
```

---

#### Excluir usuário

```bash
DELETE /user/{id}
```

---

### Livros (`/book`)

#### Criar livro

```bash
POST /book
```

```json
{
  "titulo": "O Hobbit",
  "autor": "J.R.R. Tolkien",
  "isbn": 8595084742,
  "dataPublicacao": "1937-09-21T00:00:00",
  "categoria": "Fantasia"
}
```

**Erros possíveis**
- `400 BAD REQUEST` – Data de publicação inválida (posterior à data atual)

---

#### Atualizar livro

```bash
PUT /book
```

---

#### Buscar livro por ID

```bash
GET /book/{id}
```

---

#### Listar livros

```bash
GET /book/list
```

---

#### Excluir livro

```bash
DELETE /book/{id}
```

---

#### Sugestão livros por usuário

```bash
GET /book/recommendations/{userId}
```

- Caso o usuário não tenha empréstimos retorna todos os livros
- FIXME - Caso tenha lido todos os livros de uma categoria e tenha apenas empréstimos dessa categoria, não retornará nada como sugestão

---

### Empréstimos (`/loan`)

#### Criar empréstimo

```bash
POST /loan
```

```json
{
  "usuario": { "id": 1 },
  "livro": { "id": 1 },
  "dataEmprestimo": "2026-01-09T00:00:00",
  "dataDevolucao": "2026-01-12T00:00:00",
  "status": "EMPRESTADO"
}
```

**Corpo da requisição**
- `dataDevolucao` – Pode ser omitido caso não tenha sido devolvido ainda
- `status` – EMPRESTADO ou DEVOLVIDO

**Erros possíveis**
- `400 BAD REQUEST` – Livro indisponível
- `400 BAD REQUEST` – Data de empréstimo inválida (posterior à data atual)
- `400 BAD REQUEST` – Data de devolução inválida (anterior ao empréstimo)
- `400 BAD REQUEST` – Data de devolução inválida (posterior à data atual)

---

#### Atualizar empréstimo

```bash
PUT /loan
```

---

#### Listar empréstimos

```bash
GET /loan/list
```

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5
- Mockito
- MockMvc
