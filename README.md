# Quote Engine — Explainable Pricing Service (Spring Boot + PostgreSQL)

A backend service that generates **insurance-style quotes** using simple pricing rules based on
**age, region, and risk score** — and returns a full **step-by-step explanation** of how the final price was calculated.

Built with **Spring Boot**, **Spring Data JPA**, **PostgreSQL**, and documented using **Swagger / OpenAPI**.

---

## Features

- Generate quotes via REST API  
- Deterministic pricing logic with rule-based adjustments  
- **Explainable pricing breakdown** (each adjustment + reason)  
- Quotes persisted in PostgreSQL  
- Retrieve saved quotes by ID  
- Auto-generated API docs via Swagger UI  
- Clean separation of:
  - Request DTO
  - Business logic
  -  Persistence layer
  - Response / explanation model

---

## How Pricing Works (High-Level)

1. Start with a base price (e.g., `1000.0`)
2. Apply adjustments based on:
   - **Age** (e.g., surcharge for under 25)
   - **Region** (e.g., multiplier for high-risk areas)
   - **Risk score** (bonus or penalty)
3. After each rule is applied:
   - the new price is recorded
   - the reason + adjustment type is stored in an **explanation list**
4. The final response includes:
   - `basePrice`
   - `finalPrice`
   - `adjustments[]` (step-by-step trail)

This mimics real insurance pricing systems that require **auditability and transparency**.

---

## Tech Stack

- Java 21  
- Spring Boot 3  
- Spring Data JPA  
- PostgreSQL  
- Lombok  
- Springdoc OpenAPI / Swagger UI

---

## API Endpoints

### **Create Quote**
`POST /api/quotes`

Request body:
```json
{
  "age": 22,
  "region": "high_risk_region",
  "riskScore": 80
}
