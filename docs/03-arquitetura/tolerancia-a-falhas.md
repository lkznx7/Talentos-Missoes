# Tolerância a Falhas

## 1. Mecanismos propostos

- **Timeouts** configuráveis por chamada externa.
- **Retentativas** com backoff exponencial e jitter para operações idempotentes.
- **Circuit breaker** para dependências externas (e-mail, IdP).
- **DLQ** e reprocessamento para mensagens não processáveis.
- **Idempotência** de consumers e endpoints.
- **Outbox** para não perder eventos.
- **Health checks** e graceful shutdown.
- **Bulkhead** por worker/tipo de fila (proposta, se carga justificar).

## 2. Cenários principais

| Cenário | Comportamento esperado |
|---|---|
| Banco indisponível | API retorna 503; retries do cliente; workers pausam |
| Broker indisponível | API ainda commita negócio+outbox; relay aguarda; lag monitorado |
| E-mail provider falha | Notificação permanece queued; retries; DLQ se exceder |
| Webhook duplicado | Dedup retorna 2xx sem reprocessar |
| Confirmação perdida após sucesso externo | Idempotência/reconciliação; registrar investigação |
| Consumidor morre | Mensagem volta à fila; processamento retomado |
| Missing config/secrets | Falha rápida na inicialização |

## 3. O que não se garante

- Execução exatamente uma vez de ponta a ponta não é garantida; garantimos at-least-once com idempotência.
- Tempo real estrito depende de rede/cliente; reconexão necessária.
- Ordem global entre domínios não garantida; ordem por partição/fluxo quando necessário.