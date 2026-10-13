# Persistência por Domínio (Proposta)

## Opções
1. **Banco compartilhado com schemas por domínio + ownership lógico** (recomendado inicialmente): menor custo operacional, FK dentro de schema, migração simples.
2. **Banco independente por serviço** (evolução): maior autonomia/custo/complexidade; sem FK cross-db; consistência eventual.

## Recomendação inicial
- Mesmo cluster PostgreSQL, schema por serviço/domínio.
- Sem acesso direto de um serviço ao schema de outro; acesso via API/evento.
- Cross-domain referências por UUID.

## Migrations
- Por serviço/schema; executadas no startup ou job dedicado (decisão pendente).
- Flyway por schema.

## Critérios de evolução para DB por serviço
- SLA divergente, carga extrema, autonomia de equipe, maturidade operacional.

## Backup/RPO/RTO
- Pendente definição formal; backups automáticos e testes de restore.