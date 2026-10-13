# Consistência e Transações

## 1. Estratégia

- Consistência forte **local** por domínio via transações ACID de banco relacional.
- Consistência eventual **entre domínios** via eventos.
- Evitar transações distribuídas (2PC) no início; se necessário, usar padrão Saga (proposto) para fluxos multi-domínio críticos.

## 2. Padrão Transactional Outbox

Para garantir durabilidade da publicação de eventos:

1. Na mesma transação do negócio, insere registro na tabela `outbox_event` (domain = ..., aggregate_id = ..., event_type = ..., payload = ...).
2. Um publicador (relay) lê outbox não publicado e publica no broker.
3. Marca como publicado com `published_at`.
4. Consumidores idempotentes processam pelo `eventId`.

Garantia: evento não é perdido se DB commitou; pode haver atraso e duplicidade. Não garante exatamente uma execução.

## 3. Candidatura: operação atômica

- Validar elegibilidade dentro da transação (ou chamada in-process) e inserir `application` + `application_history` + (se aplicável) outbox `application.created`.
- Restrição `UNIQUE(talent_id, mission_id)` impede duplicidade no nível de banco.
- Idempotência por chave de requisição se cliente reenviar (ver API).

## 4. Concorrência

- Use constraints/índices para invariants (UNIQUE).
- Locks otimistas (`version`/`updated_at`) para edições concorrentes (proposto).
- Isolation level adequado (Read Committed padrão, Serializable se necessário) documentar por operação.

## 5. Erros parciais

- Se falhar após commit local mas antes de publicar evento, outbox mantém pendente → relay reprocessa.
- Se falhar antes de commit, nada persiste.
- Consumers devem tolerar duplicados e reordenação.

## 6. Transições de estado

- Transições de candidatura devem ser validadas por tabela de estados permitidos (proposta).
- Mudanças de estado + histórico em mesma transação.
- Evento `application.status_changed` publicado via outbox.