# Cenários de Falha

1. DB indisponível: APIs críticas retornam 503; workers adiam.
2. Broker indisponível: negócio commita + outbox; relay retenta; lag cresce.
3. Provedor de e-mail indisponível: notificações falham e vão a retry/DLQ.
4. Token expirado durante requisição: 401; cliente refresh.
5. Duplicate webhook: segunda entrega dedup, 202 sem reprocessar.
6. Reentrega de evento: consumer idempotente no-op.
7. Timeout em S3/S2 sync calls: gateway/serviço retorna 504/503 conforme política.
8. Consumer crash: mensagem retorna à fila.
9. Confirmação perdida após aceite externo: reconciliación manual e logs.<|end|>