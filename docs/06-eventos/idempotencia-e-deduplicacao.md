# Idempotência e Deduplicação

## 1. Produtores
- Eventos possuem `eventId` único (UUID).
- Outbox garante persistência; relay pode reenviar → duplicidade esperada.

## 2. Consumidores
- Tabela `processed_event (event_id PK, processed_at)` por consumidor ou verificação por efeito.
- Se evento já processado, ack sem reprocessar.
- Efeitos devem ser idempotentes por natureza (ex.: upsert, incremento condicional).

## 3. Endpoints
- Idempotency-Key para criações (candidatura) proposta.
- Validação de assinatura + `eventId` para webhooks.

## 4. Concorrência
- Uso de UNIQUE constraints e upsert atômico.
- Race conditions devem resultar em mesmo estado, nãoErro, ou 409 compreensível.