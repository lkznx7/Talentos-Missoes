# Matriz de Rastreabilidade

| Requisito | Regra | Caso de uso | Serviço | Endpoint/Evento | Entidade | Teste |
|---|---|---|---|---|---|---|
| RF-020 | RN-001 | UC-020 | S3/candidaturas-service | API-030 / EVT-001 | application | TC-020-1 |
| RF-021 | RN-002 | UC-021 | S3/candidaturas-service | API-030 | application unique | TC-021-1/2 |
| RF-030/033 | RN-004 | UC-030/033 | S4/notificacoes-service | EVT-001 / API-042 | notification | TC-031/033 |
| RF-070/071 | RN-014/015 | UC-070/071 | S8/integracoes-service | API-070 / EVT-007 | external_event | TC-070/071 |
| RF-060 | RN-016 | UC-060 | S7/identidade-service | API-001..004 | session/user | TC-060 |
| RF-010/011 | RN-006 | UC-010/011 | S2/empresas-missoes-service | API-020/022 | company/mission | pendente |
| RF-001 | RN-006 | UC-001 | S1/talentos-service | API-010..013 | talent | pendente |
| RF-040/041 | - | UC-040/041 | S5/indicadores-service | API-050/051 / EVT-001 | metric_event | pendente |

Lacunas: endpoints detalhados e testes para S1/S2/S5/S6 completar quando contratos forem aprovados.