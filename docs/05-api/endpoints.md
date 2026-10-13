# Endpoints (Resumo)

Convenção `API-XXX`. Cada operação referenciada no OpenAPI.

## 1. Auth (S7)

| ID | Método | Caminho | Req/Auth | RF/UC |
|---|---|---|---|---|
| API-001 | POST | /v1/auth/login | público | RF-060/UC-060 |
| API-002 | POST | /v1/auth/refresh | público | RF-061/UC-060 |
| API-003 | POST | /v1/auth/logout | bearer | RF-064/UC-063 |
| API-004 | GET | /v1/auth/me | bearer | RF-060/UC-060 |

## 2. Talentos (S1)

| ID | Método | Caminho | Auth |
|---|---|---|---|
| API-010 | POST | /v1/talents | bearer (talent) |
| API-011 | GET | /v1/talents/{talentId} | bearer |
| API-012 | PATCH | /v1/talents/{talentId} | bearer (owner/admin) |
| API-013 | DELETE | /v1/talents/{talentId} | bearer (owner/admin) |
| API-014 | GET | /v1/talents/{talentId}/skills | bearer |
| API-015 | PUT | /v1/talents/{talentId}/skills | bearer (owner) |

## 3. Empresas e Missões (S2)

| ID | Método | Caminho | Auth |
|---|---|---|---|
| API-020 | POST | /v1/companies | bearer |
| API-021 | GET | /v1/companies/{companyId} | bearer |
| API-022 | POST | /v1/missions | bearer (company_admin) |
| API-023 | GET | /v1/missions | bearer (público autenticado) |
| API-024 | GET | /v1/missions/{missionId} | bearer |
| API-025 | PATCH | /v1/missions/{missionId} | bearer (owner) |
| API-026 | POST | /v1/missions/{missionId}/publish | bearer (owner) |
| API-027 | POST | /v1/missions/{missionId}/close | bearer (owner) |

## 4. Candidaturas (S3)

| ID | Método | Caminho | Auth |
|---|---|---|---|
| API-030 | POST | /v1/missions/{missionId}/applications | bearer (talent) |
| API-031 | GET | /v1/applications/{applicationId} | bearer |
| API-032 | GET | /v1/talents/{talentId}/applications | bearer |
| API-033 | GET | /v1/companies/{companyId}/applications | bearer |
| API-034 | POST | /v1/applications/{applicationId}/transitions | bearer (company/system) |

## 5. Notificações (S4)

| ID | Método | Caminho | Auth |
|---|---|---|---|
| API-040 | GET | /v1/notifications | bearer |
| API-041 | POST | /v1/notifications/{notificationId}/read | bearer |
| API-042 | POST | /v1/admin/notifications/reprocess | bearer (operator) |

## 6. Indicadores (S5)

| ID | Método | Caminho | Auth |
|---|---|---|---|
| API-050 | GET | /v1/metrics/overview | bearer |
| API-051 | GET | /v1/metrics/timeseries | bearer |

## 7. Comunicação (S6, pendente)

| ID | Método | Caminho/Mecanismo | Auth |
|---|---|---|---|
| API-060 | WS | /v1/realtime | bearer (pendente escopo) |
| API-061 | GET | /v1/conversations/{id}/messages | bearer |

## 8. Integrações (S8)

| ID | Método | Caminho | Auth |
|---|---|---|---|
| API-070 | POST | /v1/webhooks/{source} | assinatura |
| API-071 | GET | /v1/integrations/events/{eventId} | bearer (operator) |

Todos os endpoints devem ser refletidos no `openapi.yaml` de forma consistente.