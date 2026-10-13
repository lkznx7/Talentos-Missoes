# Dicionário de Dados

Campos comuns:

- `id`: UUID, PK.
- `created_at`/`updated_at`: timestamp UTC.
- `deleted_at`: timestamp UTC nullable (soft delete).
- `status`: enum textual.

## Entidades

### identity.user
| Campo | Tipo | Restrições | Exemplo |
|---|---|---|---|
| id | uuid | PK | `111...` |
| email | varchar(320) | UNIQUE, NOT NULL | `user@example.com` |
| password_hash | varchar(255) | NOT NULL | `$2a$...` |
| display_name | varchar(120) | NOT NULL | `Maria` |
| status | varchar(20) | CHECK | `active` |

### talentos.talent
| Campo | Tipo | Restrições | Exemplo |
|---|---|---|---|
| id | uuid | PK | |
| user_id | uuid | UNIQUE, NOT NULL (referência lógica) | |
| full_name | varchar(200) | NOT NULL | `Maria Silva` |
| headline | varchar(200) | nullable | `Desenvolvedora Backend` |
| status | varchar(20) | CHECK active/inactive | `active` |

### empresas_missoes.mission
| Campo | Tipo | Restrições | Exemplo |
|---|---|---|---|
| id | uuid | PK | |
| company_id | uuid | NOT NULL | |
| title | varchar(200) | NOT NULL | `Missão Java` |
| status | varchar(20) | CHECK draft/published/paused/closed | `published` |
| deadline | timestamptz | nullable | `2026-11-01T00:00:00Z` |

### candidaturas.application
| Campo | Tipo | Restrições | Exemplo |
|---|---|---|---|
| id | uuid | PK | |
| mission_id | uuid | NOT NULL | |
| talent_id | uuid | NOT NULL | |
| status | varchar(20) | CHECK | `created` |
| version | bigint | NOT NULL default 0 | 3 |
| UNIQUE | (mission_id,talent_id) | - | - |

### notificacoes.notification
| Campo | Tipo | Restrições | Exemplo |
|---|---|---|---|
| id | uuid | PK | |
| user_id | uuid | NOT NULL | |
| status | varchar(30) | CHECK queued/sent/failed/dead_lettered/read | `queued` |

### integracoes.external_event
| Campo | Tipo | Restrições | Exemplo |
|---|---|---|---|
| dedup_key | varchar(500) | UNIQUE | `partner-a:evt_123` |
| status | varchar(30) | CHECK received/processed/failed/dead_lettered | `received` |

### shared.outbox_event
| Campo | Tipo | Restrições | Exemplo |
|---|---|---|---|
| payload | jsonb | NOT NULL | `{...}` |
| status | varchar(20) | CHECK pending/published/failed | `pending` |