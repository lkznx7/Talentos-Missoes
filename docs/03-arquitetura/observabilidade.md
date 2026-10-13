# Observabilidade

## 1. Sinais

- **Logs:** estruturados (JSON), com `service`, `env`, `correlationId`, `traceId`, `userId` (sem PII), `errorCode`.
- **Métricas:** RED para APIs (rate, errors, duration), USE para recursos; lag/fila, DLQ, outbox pendente.
- **Tracing:** spans cross-process para fluxos críticos; propagação de contexto via headers (`traceparent`).

## 2. Correlation/Trace

- `X-Correlation-Id` gerado na borda se não fornecido; propagado por eventos via envelope.
- Cada evento carrega `traceId`/`spanId` quando disponível.

## 3. Dashboards/alertas propostos

- Disponibilidade/latência de APIs.
- Erro 5xx e autenticação.
- Tamanho/idade de filas.
- DLQ não vazia.
- Outbox lag.
- Falhas de integrações externas.
- Ocupação de recursos (CPU/memória).

## 4. Auditoria de negócio

- Mudanças de estado de candidatura, papéis/permissões e ações de operador registradas com ator e timestamp.