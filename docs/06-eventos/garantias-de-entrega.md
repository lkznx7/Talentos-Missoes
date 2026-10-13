# Garantias de Entrega

## 1. O que é garantido (proposta)
- **Persistência de negócio:** commit local ACID no domínio produtor.
- **Eventos (outbox):** se negócio commitou, evento será publicado eventualmente (at-least-once).
- **Consumo:** at-least-once; processamento idempotente.
- **Webhook inbound:** persistência durável antes de 2xx; processamento assíncrono.

## 2. O que não é garantido
- Execução exatamente uma vez fim-a-fim.
- Ordem global.
- Latência zero.
- Processamento síncrono após webhook 2xx.

## 3. Recuperação
- Relay reprocessa outbox não publicado.
- Consumers usam retries com backoff.
- Mensagens não processáveis vão para DLQ.
- Reprocessamento manual/automatizado com idempotência.

## 4. Monitoramento
- Outbox lag, fila size/age, DLQ depth, retry rate.