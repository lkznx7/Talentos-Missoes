# ADR-010 — Observabilidade

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-005

## 1. Contexto
Padronizar logs, métricas, traces.

## 2. Forças e requisitos
Correlação entre componentes; operação.

## 3. Alternativas consideradas
A) OpenTelemetry. B) Ferramentas específicas (Prometheus, ELK, vendor).

## 4. Decisão
Proposta: **OpenTelemetry-compatível para traces/metrics, logs estruturados JSON**, com tooling final pendente.

## 5. Justificativa
Portabilidade.

## 6. Consequências
### Positivas
Vendor-neutral.
### Negativas
Configuração extra.
### Riscos
Overhead se não calibrado.

## 7. Impactos
SDKs, infraestrutura.

## 8. Critérios de validação
Traces cross-service em staging.

## 9. Condições de revisão
Se custo/complexidade não justificar.

## 10. Referências
`docs/03-arquitetura/observabilidade.md`