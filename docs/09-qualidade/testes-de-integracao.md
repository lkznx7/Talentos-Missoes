# Testes de Integração

- Usar Testcontainers para PostgreSQL e broker.
- Testar repositories, migrations, outbox/relay.
- Testar integração Gateway→serviço com Testcontainers/network.
- Testar webhook validação/replay/dedup.
- Testar concorrência de candidatura duplicada (TC-021-2).
- Testar notificações DLQ/reprocessamento.