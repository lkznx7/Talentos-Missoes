# Testes de Contrato

- Consumidores do Gateway/OpenAPI podem usar Schemathesis ou Dredd (proposto) em CI.
- Eventos: validar exemplos em `06-eventos/contratos` contra schema (pendente schema JSON).
- Testar compatibilidade backward de OpenAPI.
- Testar rotas protegidas e payloads de erro padronizados.
- Não prometer cobertura total sem evidência; iniciar com endpoints críticos.