# Catálogo de Eventos

Convenção de nomes: `{dominio}.{agregado}.{acao}` em snake? Proposta usar `application.created` etc. Versionamento por `schemaVersion`.

| ID | Evento | Produtor | Consumidores | Momento | Propósito |
|---|---|---|---|---|---|
| EVT-001 | `application.created` | S3 | S4, S5, S6? | Após commit da candidatura | Notificar, métricas, chat |
| EVT-002 | `application.status_changed` | S3 | S4, S5 | Após transição de estado | Notificar, métricas |
| EVT-003 | `mission.published` | S2 | S4 (opcional), S5 | Após publicação | Notificar seguidores? (pendente), métricas |
| EVT-004 | `mission.closed` | S2 | S3? (pendente), S4, S5 | Após encerramento | Notificar candidatos, bloquear candidaturas |
| EVT-005 | `talent.profile.updated` | S1 | S5, S3 (se elegibilidade) | Após atualização | Métricas, revalidar |
| EVT-006 | `identity.user.created` | S7 | S1 (criar perfil?), S4 | Após criação de identidade | Bootstrap perfil/notificação |
| EVT-007 | `integration.event.received` | S8 | S8 workers / domínios | Após persistir webhook | Processamento assíncrono |
| EVT-008 | `notification.created` | S4 | S5 | Após criação notificação | Métricas |
| EVT-009 | `message.sent` (se S6) | S6 | S4, S5 | Após enviar mensagem | Notificar, métricas |

## Envelope padrão de evento

```json
{
  "eventId": "uuid",
  "eventType": "application.created",
  "schemaVersion": "1.0.0",
  "occurredAt": "2026-10-13T12:00:00Z",
  "producer": "S3",
  "aggregate": { "type": "Application", "id": "uuid" },
  "correlationId": "uuid",
  "traceId": "hex",
  "payload": { }
}
```

## Compatibilidade
- Adicionar campos opcionais = compatível.
- Remover/renomear campos = nova major version ou evento novo.
- Consumers devem ignorar campos desconhecidos.

## Ordenação e duplicidade
- Eventos podem ser duplicados e atrasados.
- Ordenação global não garantida; se necessária, por partição por `aggregate.id`.
- Consumers idempotentes por `eventId`.