# ADR-011 — Containerização e Deploy

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-001

## 1. Contexto
Definir execução local e produção.

## 2. Forças e requisitos
Portabilidade, repetibilidade, rollback.

## 3. Alternativas consideradas
A) Docker containers + Compose. B) Plataforma gerenciada.

## 4. Decisão
Proposta: **Docker para artefatos; Compose para desenvolvimento local**; produção conforme infra disponível (containers/managed).

## 5. Justificativa
Reprodutibilidade.

## 6. Consequências
### Positivas
Ambiente consistente.
### Negativas
Complexidade de desenvolvimento local.
### Riscos
Paridade dev/prod imperfect.

## 7. Impactos
Pipelines, infra.

## 8. Critérios de validação
`docker compose up` reproduzível.

## 9. Condições de revisão
Se plataforma gerenciada simplifique.

## 10. Referências
`docs/03-arquitetura/deployment.md`