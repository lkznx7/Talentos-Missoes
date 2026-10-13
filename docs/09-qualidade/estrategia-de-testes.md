# Estratégia de Testes

## Pirâmide
- Testes unitários de domínio e aplicação.
- Testes de integração com Testcontainers (Postgres, broker).
- Testes de contrato (OpenAPI/AsyncAPI quando existir) quando houver consumidores independentes.
- Testes end-to-end para fluxos críticos (frontend+gateway+backend).
- Testes de resiliência (timeouts, retries, DLQ) em ambiente controlado.

## Cobertura esperada
- Regras críticas (dedup, transições, autorização) com testes unitários.
- APIs principais com testes de integração.
- Migrations com Testcontainers.

## Ambientes
- CI: unit + integração em PR.
- Stage: e2e e carga (hipótese).