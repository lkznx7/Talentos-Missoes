# Modelo Lógico (Proposta)

Banco relacional. Proposta inicial: **schema por domínio** no mesmo cluster/banco; evolução para bancos independentes conforme carga e autonomia. Cross-domínio sem FK física.

## 1. S7 — Identidade (schema `identity`)

- `identity.user` (PK id uuid) email unique, password_hash, display_name, status, created_at, updated_at, deleted_at
- `identity.role` (id, code unique, description)
- `identity.permission` (id, code unique, description)
- `identity.user_role` (user_id FK, role_id FK, PK composite)
- `identity.role_permission` (role_id FK, permission_id FK, PK composite)
- `identity.session` (id, user_id FK, refresh_token_hash, expires_at, revoked_at, created_at, ip_address, user_agent)
- `identity.outbox_event` (infra de publicação)

## 2. S1 — Talentos (schema `talentos`)

- `talentos.talent` (id, user_id unique, full_name, headline, summary, location, status, created_at, updated_at, deleted_at)
- `talentos.skill` (id, talent_id FK, name, level, years_experience, unique(talent_id,name))
- `talentos.experience` (id, talent_id FK, company_name, title, start_date, end_date, description)
- `talentos.portfolio_item` (id, talent_id FK, title, url, description, item_type, created_at)

## 3. S2 — Empresas e Missões (schema `empresas_missoes`)

- `empresas_missoes.company` (id, legal_name, trade_name, website, status, created_by_user_id, created_at, updated_at)
- `empresas_missoes.company_member` (company_id FK, user_id, member_role, created_at, PK composite)
- `empresas_missoes.mission` (id, company_id FK, title, description, status, requirements jsonb, deadline, published_at, closed_at, created_at, updated_at)

## 4. S3 — Candidaturas (schema `candidaturas`)

- `candidaturas.application` (id, mission_id, talent_id, status, eligibility_snapshot jsonb, created_at, updated_at, version)
- `candidaturas.application_history` (id, application_id FK, from_state, to_state, changed_by, reason, occurred_at)
- Restrição: unique(mission_id, talent_id)

## 5. S4 — Notificações (schema `notificacoes`)

- `notificacoes.notification` (id, user_id, type, title, body, status, created_at, read_at)
- `notificacoes.notification_attempt` (id, notification_id FK, attempted_at, outcome, provider_message_id, failure_reason)

## 6. S5 — Indicadores (schema `indicadores`)

- `indicadores.metric_event` (id, event_id unique, event_name, aggregate_type, aggregate_id, occurred_at, dimensions jsonb)
- `indicadores.metric_series` (metric_name, granularity, bucket_start, value, updated_at, PK composite)

## 7. S6 — Comunicação (schema `comunicacao`, se escopo confirmado)

- `comunicacao.conversation` (id, company_id, talent_id, mission_id nullable, created_at)
- `comunicacao.message` (id, conversation_id FK, sender_user_id, content_type, content, sent_at, delivered_at, read_at)

## 8. S8 — Integrações (schema `integracoes`)

- `integracoes.external_event` (id, source, external_event_id, dedup_key unique, received_at, status, payload_ref, attempt_count, processed_at, failure_reason)
- `integracoes.webhook_delivery` (id, target, event_ref, status, attempts, last_attempt_at, next_retry_at)

## 9. Infraestrutura compartilhada

- `shared.outbox_event` (id, service_name, aggregate_type, aggregate_id, event_type, schema_version, payload jsonb, occurred_at, published_at, status, attempts)
- `shared.processed_event` (consumer_name, event_id, processed_at, PK composite) por consumidor ou schema do consumidor.