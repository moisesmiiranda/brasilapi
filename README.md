# 📌 API de Feriados Nacionais - BrasilAPI

Esta é uma API REST desenvolvida em **Java 21** com **Spring Boot 3**, que consome a [BrasilAPI](https://brasilapi.com.br/) para consultar feriados nacionais. O projeto utiliza **Gradle** como gerenciador de dependências e inclui documentação via **Swagger UI**.

---

## 🚀 Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3**
- **Gradle**
- **Spring Cloud OpenFeign** (para consumo de API externa via OpenFeign)
- **Springdoc OpenAPI** (para documentação Swagger)

---

## 📥 Instalação e Execução

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/moisesmiiranda/brasilapi/.git
cd seu-repositorio
```

### 2️⃣ Compilar o projeto
```bash
./gradlew build
```

### 3️⃣ Executar a API
```bash
./gradlew bootRun
```

A API estará rodando em `http://localhost:8080`

---

## 📖 Uso da API

### 🔹 Endpoint: Obter Feriados por Ano

- **Rota:** `GET /feriados/{ano}`
- **Exemplo de Requisição:**
  ```bash
  curl -X GET "http://localhost:8080/feriados/2026"
  ```
- **Exemplo de Resposta:**
  ```json
  [
    {
      "data": "2026-01-01",
      "nome": "Confraternização Universal",
      "tipo": "national"
    },
    {
      "data": "2026-04-21",
      "nome": "Tiradentes",
      "tipo": "national"
    }
  ]
  ```

---

## 📄 Documentação Swagger
A API possui documentação interativa via Swagger UI:

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🧪 Testes de Integração

Os testes de integração estão localizados em `src/test/java/.../controller/HolidayControllerIntegrationTest.java` e utilizam **JUnit 5** com **Mockito** via `@MockBean`, carregando o contexto completo da aplicação com `@SpringBootTest`.

### Cenários cobertos

| Cenário | Resultado esperado |
|---|---|
| Ano com múltiplos feriados | `200 OK` + lista completa |
| Ano sem feriados | `200 OK` + lista vazia `[]` |
| Delegação do ano correto ao client | Verificação via `verify(mock)` |
| Client externo lança exceção | `500 Internal Server Error` |
| Ano com único feriado | `200 OK` + objeto correto |

### Executar os testes

```bash
./gradlew test
```

Relatório gerado em `build/reports/tests/test/index.html`.

---

## 📜 Licença
Este projeto é distribuído sob a licença **MIT**.

---

Feito por [Moisés Miranda](https://github.com/moisesmiiranda).

