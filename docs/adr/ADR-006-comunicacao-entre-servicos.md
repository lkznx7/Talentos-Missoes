# ADR-006 — Comunicação entre Componentes

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-001, ADR-008, ADR-015

## 1. Contexto
Definir critérios para REST, eventos, SSE, WebSocket, RPC, GraphQL.

## 2. Forças e requisitos
Latência, direção do fluxo, consistência, simplicidade.

## 3. Alternativas consideradas
A) REST para tudo. B) REST + eventos + WebSocket/SSE condicional. C) GraphQL/RPC broad.

## 4. Decisão
Proposta: **REST para CRUD/consultas, eventos para side effects, SSE/WebSocket apenas para realtime confirmado, sem GraphQL/RPC inicial**.

## 5. Justificativa
Menor complexidade e alinhamento aos fluxos.

## 6. Consequências
### Positivas
Previsibilidade e tooling.
### Negativas
Mais endpoints; necessidade de consumers idempotentes.
### Riscos
Chamadas síncronas em cadeia.

## 7. Impactos
Contratos, testes, Gateway.

## 8. Critérios de validação
Medição de latência e acoplamento.

## 9. Condições de revisão
Se realtime/requisitos de agregação exigirem outra abordagem.

## 10. Referências
`docs/03-arquitetura/comunicacao-entre-componentes.md`