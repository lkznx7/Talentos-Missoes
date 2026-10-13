# Integridade e Restrições

- IDs são UUID.
- UNIQUE para e-mail (user), `(talent_id,name)` (skill), `(company_id,user_id)` (company_member), `(mission_id,talent_id)` (application), `dedup_key` (external_event).
- CHECK constraints para estados (status).
- FK intra-schema/domínio permitidas.
- Cross-domain: referência lógica por UUID sem FK física se bancos separados.
- Locks otimistas via `version` em `application` e `mission` (proposto).
- Soft delete em perfis/empresas quando necessário; tabelas de auditoria imutáveis.
- Cascata: ao remover talento (soft), candidaturas permanecem para auditoria (política pendente).
- Outbox e processed events garantem durabilidade e idempotência.