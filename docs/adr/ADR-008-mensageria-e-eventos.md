# ADR-008 — Mensageria e Eventos

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-014

## 1. Contexto
Notificações, indicadores, integrações precisam de processamento assíncrono durável.

## 2. Forças e requisitos
Durabilidade, recuperação, ordenação, custo.

## 3. Alternativas consideradas
A) Broker RabbitMQ. B) Kafka. C) SQS gerenciado (se cloud). D) chamadas diretas.

## 4. Decisão
Proposta: **usar broker externo + Transactional Outbox**; tecnologia exata pendente (RabbitMQ recomendado para simplicidade inicial, Kafka se alto throughput).

## 5. Justificativa
Durabilidade e desacoplamento.

## 6. Consequências
### Positivas
Processamento resiliente.
### Negativas
Complexidade operacional.
### Riscos
Duplicidade e ordenação.

## 7. Impactos
Relay, consumers, DLQ, monitoramento.

## 8. Critérios de validação
Testes de falha e DLQ.

## 9. Condições de revisão
Throughput/requisitos mudam.

## 10. Referências
`docs/06-eventos/garantias-de-entrega.md`