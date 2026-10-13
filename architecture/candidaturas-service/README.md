# candidaturas-service

## Árvore
```
candidaturas-service/
├── src/main/java/com/talentosemissoes/candidaturas/
├── src/main/resources/db/migration/
├── src/test/
├── Dockerfile
└── pom.xml
```

## Responsabilidade
- Candidaturas, estados, histórico, dedup, concorrência.

## Dependências
- Spring Boot 4.1.x, Spring MVC, Validation, Security Resource Server (sync client opcional), JPA, PostgreSQL, Flyway, Actuator
- Mensageria producer/consumer
- Idempotency-Key suporte (implementação)