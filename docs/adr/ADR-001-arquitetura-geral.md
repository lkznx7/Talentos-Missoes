# ADR-001 — Arquitetura Geral e Unidade de Implantação

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-002, ADR-007, ADR-011

## 1. Contexto
A documentação inicial recomendou monólito modular. A arquitetura-alvo proposta agora considera oito microsserviços Java 25 independentes com Gateway e frontend separado. As duas visões coexistem e precisam ser reconciliadas.

## 2. Forças e requisitos
Consistência local, complexidade operacional, autonomia, escalabilidade, custo e maturidade da equipe.

## 3. Alternativas consideradas
A) Monólito modular. B) Oito microsserviços independentes desde o início. C) Abordagem evolutiva: módulos bem definidos com extração incremental.

## 4. Decisão
Proposta: iniciar como **monólito modular ou vertical slices bem isolados**, com domínios claros e independência de deploy apenas quando critérios forem atendidos. A arquitetura de oito serviços permanece como alvo de evolução.

## 5. Justificativa
Reduz custo inicial e risco de consistência distribuída, preservando capacidade de extração.

## 6. Consequências
### Positivas
Menor complexidade inicial; transações locais.
### Negativas
Deploy unitário; risco de acoplamento interno.
### Riscos e limitações
Se boundary não for respeitada, a migração futura será difícil.

## 7. Impactos na implementação e operação
Módulos por pacote/domínio; build e testes podem ser centralizados.

## 8. Critérios de validação
Limites de API internos, ausência de acesso cross-domain às tabelas, métricas de acoplamento.

## 9. Condições para revisão
Necessidade de deploy independente, escala disparada, múltiplas equipes.

## 10. Referências
`03-arquitetura/visao-geral.md`, `docs/03-arquitetura/arquitetura-backend.md`