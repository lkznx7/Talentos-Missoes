# Migrations (Estratégia)

## Nomenclatura
- Flyway: `V{timestamp}__{descricao}.sql` ou `V{major}_{minor}__...`.
- Um script por mudança de schema, pequeno e testável.

## Compatibilidade
- Expand/contract para mudanças em tabelas usadas em produção.
- Colunas novas nullable ou com default; remoção apenas após versão sem uso.
- Migrations devem ser idempotentes ou versionadas; testar em banco limpo e em upgrades.

## Execução
- Rodar migrations no startup para serviços com DB (ou job dedicado).
- Em produção, verificar backup antes de migration destrutiva.
- CI valida migration contra Postgres com Testcontainers (proposto).