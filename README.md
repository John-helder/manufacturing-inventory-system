# 🏭 Manufacturing Inventory System

Sistema de controle de estoque para manufatura, desenvolvido em Java com Spring Boot. O sistema permite gerenciar produtos, matérias-primas e o vínculo entre eles, calculando automaticamente a capacidade de produção com base no estoque disponível.

> Projeto iniciado como teste técnico para uma vaga e continuado como projeto de aprendizado em Java e Spring Boot.

---

## 📋 Funcionalidades

- Cadastro, listagem, edição e exclusão de **Produtos**
- Cadastro, listagem, edição e exclusão de **Matérias-primas**
- Vínculo entre produtos e matérias-primas com quantidade necessária
- Cálculo automático de **disponibilidade de produção** com base no estoque atual

---

## 🛠️ Tecnologias utilizadas

- Java 21
- Spring Boot 3.5
- Spring Data JPA + Hibernate
- PostgreSQL
- Maven
- Lombok
- Swagger/OpenAPI (SpringDoc 2.5)
- JUnit 5 + Mockito (testes unitários)

---

## 🗂️ Estrutura do projeto

```
src/
└── main/
    └── java/com/johnhelder/inventory/
        ├── controller/       # Camada REST (endpoints da API)
        ├── domain/           # Entidades JPA
        ├── dto/              # Objetos de transferência de dados
        ├── repository/       # Interfaces Spring Data JPA
        └── service/          # Lógica de negócio (interfaces + implementações)
└── test/
    └── java/com/johnhelder/inventory/
        └── service/          # Testes unitários dos services
```

---

## ⚙️ Como executar localmente

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL

### Configuração do banco de dados

Crie um banco de dados no PostgreSQL:

```sql
CREATE DATABASE manufacturing_inventory;
```

Configure as credenciais no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/manufacturing_inventory
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### Rodando o projeto

```bash
# Clone o repositório
git clone https://github.com/John-helder/manufacturing-inventory-system.git

# Acesse a pasta do projeto
cd manufacturing-inventory-system

# Execute com Maven
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 📡 Endpoints da API

### Produtos — `/api/products`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/products` | Cria um novo produto |
| GET | `/api/products` | Lista todos os produtos |
| GET | `/api/products/{id}` | Busca produto por ID |
| PUT | `/api/products/{id}` | Atualiza um produto |
| DELETE | `/api/products/{id}` | Remove um produto |
| GET | `/api/products/production-availability` | Retorna disponibilidade de produção |

**POST /api/products** — Exemplo de requisição:
```json
{
  "code": "PROD001",
  "name": "Cadeira Ergonômica",
  "value": 499.90
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "code": "PROD001",
  "name": "Cadeira Ergonômica",
  "value": 499.90
}
```

**GET /api/products/production-availability** — Exemplo de resposta:
```json
[
  {
    "productId": 1,
    "productName": "Cadeira Ergonômica",
    "canProduce": true,
    "maxQuantity": 15
  }
]
```

---

### Matérias-primas — `/api/raw-materials`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/raw-materials` | Cria uma nova matéria-prima |
| GET | `/api/raw-materials` | Lista todas as matérias-primas |
| GET | `/api/raw-materials/{id}` | Busca matéria-prima por ID |
| PUT | `/api/raw-materials/{id}` | Atualiza uma matéria-prima |
| DELETE | `/api/raw-materials/{id}` | Remove uma matéria-prima |

**POST /api/raw-materials** — Exemplo de requisição:
```json
{
  "code": "RM001",
  "name": "Aço",
  "quantity": 500
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "code": "RM001",
  "name": "Aço",
  "stockQuantity": 500
}
```

---

### Vínculo Produto x Matéria-prima — `/api/product-raw-materials`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/product-raw-materials` | Vincula matéria-prima a um produto |
| GET | `/api/product-raw-materials` | Lista todos os vínculos |
| GET | `/api/product-raw-materials/{id}` | Busca vínculo por ID |
| PUT | `/api/product-raw-materials/{id}` | Atualiza um vínculo |
| DELETE | `/api/product-raw-materials/{id}` | Remove um vínculo |

**POST /api/product-raw-materials** — Exemplo de requisição:
```json
{
  "productId": 1,
  "rawMaterialId": 1,
  "quantityRequired": 4
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "productId": 1,
  "productName": "Cadeira Ergonômica",
  "rawMaterialId": 1,
  "rawMaterialName": "Aço",
  "quantityRequired": 4
}
```

---

## 🧪 Testes

Para executar os testes unitários:

```bash
./mvnw test
```

---

## 🔜 Próximos passos

- [ ] Tratamento global de exceções com `@ControllerAdvice`
- [ ] Expandir cobertura de testes unitários
- [x] Documentação da API com Swagger/OpenAPI
- [ ] Validações customizadas nos DTOs

---

## 👨‍💻 Autor

**John Helder**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white)](https://linkedin.com/in/seu-perfil)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat&logo=github&logoColor=white)](https://github.com/John-helder)