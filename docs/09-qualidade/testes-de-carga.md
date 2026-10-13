# Testes de Carga

- Baseline de performance após implementação (hipótese), usando ferramentas a definir.
- Cenários: login, listagem de missões, criação de candidatura, webhook flood, notificação backlog.
- Métricas: latência (P95/P99), throughput, erros, lag de fila, uso de DB.
- Não inventar SLA; estabelecer com produto/ops.
- Executar em ambiente stage representativo, não local.