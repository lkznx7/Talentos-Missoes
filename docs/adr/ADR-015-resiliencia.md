# ADR-015 — Resiliência

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-006, ADR-008

## 1. Contexto
Definir timeouts, retries, circuit breakers.

## 2. Forças e requisitos
Disponibilidade, recuperação.

## 3. Alternativas consideradas
A) Retries ilimitados. B) Timeouts/retries controlados. C) Circuit breaker imediato.

## 4. Decisão
Proposta: **timeouts configuráveis, retries com backoff/jitter apenas para operações idempotentes, circuit breaker em integrações externas**.

## 5. Justificativa
Controla efeitos colaterais.

## 6. Consequências
### Positivas
Menos cascata de falhas.
### Negativas
Retry storm se mal calibrado.
### Riscos
Complexidade de configuração.

## 7. Impactos
Gateway, integrações, notificações.

## 8. Critérios de validação
Testes de chaos/falha.

## 9. Condições de revisão
Se custo de resiliência superar SLA.

## 10. Referências
`docs/03-arquitetura/tolerancia-a-falhas.md`