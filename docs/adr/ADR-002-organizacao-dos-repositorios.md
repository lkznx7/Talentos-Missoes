# ADR-002 — Organização dos Repositórios

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-001, ADR-012

## 1. Contexto
Definir monorepo vs multirepo para frontend, gateway e serviços.

## 2. Forças e requisitos
Coordenação de contratos, pipelines, versionamento, times.

## 3. Alternativas consideradas
A) Monorepo com pacotes por componente. B) Multirepo por serviço/componente.

## 4. Decisão
Proposta: **monorepo** inicialmente, com builds/pipelines afetados por caminhos (affected).

## 5. Justificativa
Facilita contratos compartilhados e consistência em estágio inicial.

## 6. Consequências
### Positivas
Visão única, versionamento sincronizado.
### Negativas
Build inicial pode crescer; necessidade de tooling affected.
### Riscos e limitações
Risco de acoplamento indevido entre pacotes.

## 7. Impactos
CI/CD, PRs, libs compartilhadas.

## 8. Critérios de validação
Tempo de build, conflitos de dependência, coordenação.

## 9. Condições para revisão
Autonomia extrema de times ou extração de serviço para repositório próprio.

## 10. Referências
`docs/adr/ADR-012-ci-cd-e-versionamento.md`