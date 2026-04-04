# 🏭 Manufacturing Inventory System

Sistema de controle de estoque para manufatura, desenvolvido em Java com Spring Boot. O sistema permite gerenciar produtos, matérias-primas e o vínculo entre eles, calculando automaticamente a capacidade de produção com base no estoque disponível.

> Projeto iniciado como teste técnico para uma vaga e continuado como projeto de aprendizado em Java e Spring Boot.

🖥️ **Frontend:** [John-helder/Inventory-Frontend](https://github.com/John-helder/Inventory-Frontend)

---

## 📋 Funcionalidades

- Cadastro, listagem, edição e exclusão de **Produtos** (com descrição, categoria e tempo de produção)
- Cadastro, listagem, edição e exclusão de **Matérias-primas** (com categoria, estoque mínimo, unidade, localização e preço unitário)
- Vínculo entre produtos e matérias-primas com quantidade necessária
- Cálculo automático de **disponibilidade de produção** com base no estoque atual
- **Tratamento global de exceções** com respostas HTTP semânticas (404, 409, 400)
- Documentação interativa via **Swagger/OpenAPI**

---

## 🛠️ Tecnologias utilizadas

- Java 21
- Spring Boot 3.5
- Spring Data JPA + Hibernate
- PostgreSQL
- Maven
- Lombok
- Swagger/OpenAPI (SpringDoc 2.8.6)
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
        ├── exception/        # Exceções customizadas e GlobalExceptionHandler
        ├── repository/       # Interfaces Spring Data JPA
        └── service/          # Lógica de negócio (interfaces + implementações)
└── test/
    └── java/com/johnhelder/inventory/
        └── service/          # Testes unitários dos services
```

---

## ⚙️ Como executar localmente

### Pré-requisitos

- Java 21+
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
spring.jpa.open-in-view=false
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

A documentação Swagger estará disponível em: `http://localhost:8080/swagger-ui/index.html`

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
  "value": 499.90,
  "description": "Cadeira ergonômica para escritório",
  "category": "Móveis",
  "productionTime": 30
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "code": "PROD001",
  "name": "Cadeira Ergonômica",
  "value": 499.90,
  "description": "Cadeira ergonômica para escritório",
  "category": "Móveis",
  "productionTime": 30
}
```

**GET /api/products/production-availability** — Exemplo de resposta:
```json
[
  {
    "productId": 1,
    "productName": "Cadeira Ergonômica",
    "canProduce": true,
    "maxQuantityPossible": 15
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
  "name": "Aço Carbono",
  "quantity": 500,
  "category": "Metal",
  "minimumQuantity": 100,
  "unit": "kg",
  "location": "A-01",
  "unitPrice": 45.90
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "code": "RM001",
  "name": "Aço Carbono",
  "stockQuantity": 500,
  "category": "Metal",
  "minimumQuantity": 100,
  "unit": "kg",
  "location": "A-01",
  "unitPrice": 45.90
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
  "rawMaterialName": "Aço Carbono",
  "quantityRequired": 4
}
```

---

## ⚠️ Tratamento de erros

A API retorna respostas padronizadas para todos os erros:

| Status | Situação |
|--------|----------|
| 400 | Dados inválidos na requisição |
| 404 | Recurso não encontrado |
| 409 | Conflito (ex: vínculo duplicado) |
| 500 | Erro interno inesperado |

**Exemplo de resposta de erro:**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Product not found",
  "timestamp": "2026-03-28T10:00:00"
}
```

---

## 🧪 Testes

Para executar os testes unitários:

```bash
./mvnw test
```

Cobertura atual:
- `RawMaterialServiceImpl` — 6 testes
- `ProductServiceImpl` — 9 testes
- `ProductRawMaterialServiceImpl` — 11 testes

---

## 🔜 Próximos passos

- [x] Tratamento global de exceções com `@ControllerAdvice`
- [x] Expandir cobertura de testes unitários
- [x] Documentação da API com Swagger/OpenAPI
- [x] Integração com frontend React
- [ ] Kanban de produção (A Fazer → Em Andamento → Em Teste → Concluído)
- [ ] Validações customizadas nos DTOs

---

## 👨‍💻 Autor

**John Helder**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white)](https://linkedin.com/in/john-helder)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat&logo=github&logoColor=white)](https://github.com/John-helder)