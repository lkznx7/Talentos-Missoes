# ADR-012 — CI/CD e Versionamento

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-002

## 1. Contexto
Definir pipeline e versionamento de contratos.

## 2. Forças e requisitos
Qualidade, compatibilidade, rollback.

## 3. Alternativas consideradas
A) Pipeline por componente. B) Pipeline monolítica.

## 4. Decisão
Proposta: **pipeline por unidade de implantação**, com execução de componentes afetados e verificações globais críticas.

## 5. Justificativa
Flexibilidade e velocidade.

## 6. Consequências
### Positivas
Deploys mais rápidos.
### Negativas
Configuração mais complexa.
### Riscos
Escopo incompleto.

## 7. Impactos
CI, releases, migrations.

## 8. Critérios de validação
Pipeline reprodutível.

## 9. Condições de revisão
Se complexidade for maior que benefício.

## 10. Referências
`docs/10-operacao/deploy-e-rollback.md`