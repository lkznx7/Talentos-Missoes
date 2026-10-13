# ADR-014 — Consistência Distribuída

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-008

## 1. Contexto
Definir transações entre domínios.

## 2. Forças e requisitos
Consistência, recuperação, idempotência.

## 3. Alternativas consideradas
A) 2PC. B) Saga. C) Outbox + eventual consistency.

## 4. Decisão
Proposta: **evitar 2PC inicialmente; transações locais + Outbox para efeitos; Saga apenas quando houver fluxo multi-domínio crítico confirmado**.

## 5. Justificativa
Menor complexidade.

## 6. Consequências
### Positivas
Transações locais simples.
### Negativas
Consistência eventual.
### Riscos
Erros parciais.

## 7. Impactos
Candidaturas, missões, notificações.

## 8. Critérios de validação
Testes de falha parcial.

## 9. Condições de revisão
Fluxo crítico multi-domínio.

## 10. Referências
`docs/03-arquitetura/consistencia-e-transacoes.md`