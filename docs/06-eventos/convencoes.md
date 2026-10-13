# Convenções de Eventos

## 1. Nomes
- Formato `{dominio}.{entidade}.{acao}` com minúsculas e pontos.
- Ações no passado: `created`, `updated`, `deleted`, `status_changed`.

## 2. Metadados obrigatórios
- `eventId` (UUID)
- `eventType`
- `schemaVersion`
- `occurredAt` (UTC)
- `producer`
- `correlationId`
- `payload`

## 3. Payload
- Deve ser autodescritivo e suficiente para consumo sem chamada síncrona quando possível.
- Evitar PII sensível; incluir IDs e estados.

## 4. Entrega
- At-least-once por padrão.
- Consumers idempotentes por `eventId`.
- DLQ após retentativas.