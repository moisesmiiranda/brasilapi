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

## 🏗️ Arquitetura

O projeto é organizado em camadas bem definidas, seguindo as boas práticas de desenvolvimento backend:

```
Cliente HTTP
     |
     v
HolidayController        ← recebe a requisição REST e delega ao serviço
     |
     v
HolidayService           ← encapsula a lógica de negócio
     |
     v
BrasilApiClient          ← Feign client que realiza a chamada HTTP externa
     |
     v
BrasilAPI (externa)      ← https://brasilapi.com.br/api/feriados/v1
```

| Camada | Responsabilidade |
|---|---|
| `HolidayController` | Recebe e valida as requisições HTTP; delega ao `HolidayService` |
| `HolidayService` | Orquestra a lógica de negócio; isola o controller do client externo |
| `BrasilApiClient` | Abstração OpenFeign para comunicação com a BrasilAPI |
| `GlobalExceptionHandler` | Intercepta exceções e retorna respostas de erro padronizadas |
| `BrasilApiClientFallbackFactory` | Aciona quando a BrasilAPI está indisponível (circuit breaker) |

---

## 🛡️ Resiliência

O client Feign é protegido por:

- **Timeouts** — `connectTimeout: 3000ms` / `readTimeout: 3000ms`
- **Circuit Breaker** (Resilience4j) — abre após 50% de falhas em janela de 5 chamadas; aguarda 10s antes de tentar novamente
- **Fallback** — `BrasilApiClientFallbackFactory` retorna `ExternalApiException` com mensagem amigável quando o circuito está aberto

---

## ⚠️ Tratamento de Erros

Todas as exceções são capturadas pelo `GlobalExceptionHandler` e retornam um `ErrorResponse` padronizado:

```json
{
  "code": "EXTERNAL_API_UNAVAILABLE",
  "message": "BrasilAPI is currently unavailable. Please try again later.",
  "timestamp": "2026-04-07T10:00:00"
}
```

| Exceção | HTTP Status | Código |
|---|---|---|
| `ExternalApiException` | `503 Service Unavailable` | `EXTERNAL_API_UNAVAILABLE` |
| `FeignException` | `502 Bad Gateway` | `EXTERNAL_API_ERROR` |
| `Exception` (genérica) | `500 Internal Server Error` | `INTERNAL_ERROR` |

---

## 🧪 Testes de Integração

Os testes de integração estão em `src/test/java/.../controller/HolidayControllerIntegrationTest.java` e utilizam **JUnit 5** com **Mockito** via `@MockitoBean`, carregando o contexto completo da aplicação com `@SpringBootTest`.

### Cenários cobertos

| Cenário | Resultado esperado |
|---|---|
| Ano com múltiplos feriados | `200 OK` + lista completa validada campo a campo |
| Ano sem feriados | `200 OK` + lista vazia `[]` |
| Delegação do ano correto ao client | Verificação via `verify(mock)` |
| Client lança `RuntimeException` | `500 Internal Server Error` + `ErrorResponse` |
| Client lança `ExternalApiException` | `503 Service Unavailable` + `ErrorResponse` |
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

