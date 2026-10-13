# Requisitos Funcionais

Convenção de IDs: `RF-XXX`. Status: Definido (contexto), Proposto, Pendente.

## 1. S1 — Talentos

| ID | Requisito | Status | Caso de uso |
|---|---|---|---|
| RF-001 | O sistema deve permitir criar, consultar, atualizar e desativar perfis de talento. | Pendente (detalhamento) | UC-001 |
| RF-002 | O sistema deve permitir registrar e manter habilidades do talento. | Pendente | UC-002 |
| RF-003 | O sistema deve permitir registrar e manter experiências profissionais. | Pendente | UC-003 |
| RF-004 | O sistema deve permitir registrar e manter portfólio (links/arquivos). | Pendente | UC-004 |
| RF-005 | O sistema deve permitir que um talento consulte seu próprio perfil e dados. | Proposta | UC-005 |
| RF-006 | O sistema deve permitir que empresas autorizadas consultem perfis de talentos candidatos. | Pendente (política) | UC-006 |

## 2. S2 — Empresas e Missões

| ID | Requisito | Status | Caso de uso |
|---|---|---|---|
| RF-010 | O sistema deve permitir criar, consultar, atualizar dados de empresas. | Pendente | UC-010 |
| RF-011 | O sistema deve permitir criar rascunho de missão. | Pendente | UC-011 |
| RF-012 | O sistema deve permitir publicar, pausar e encerrar missões. | Pendente | UC-012 |
| RF-013 | O sistema deve permitir definir requisitos e prazos de missões. | Pendente | UC-013 |
| RF-014 | O sistema deve permitir consultar missões (filtros/paginação). | Proposta | UC-014 |
| RF-015 | O sistema deve impedir alteração incompatível de missões com candidaturas (regra a definir). | Pendente | UC-015 |

## 3. S3 — Candidaturas

| ID | Requisito | Status | Caso de uso |
|---|---|---|---|
| RF-020 | O sistema deve registrar a candidatura de um talento a uma missão elegível. | Definido (contexto) | UC-020 |
| RF-021 | O sistema deve impedir candidatura duplicada do mesmo talento à mesma missão. | Definido (contexto) | UC-021 |
| RF-022 | O sistema deve manter estado e histórico da candidatura. | Definido (contexto) | UC-022 |
| RF-023 | O sistema deve permitir transições de estado auditáveis. | Proposta | UC-023 |
| RF-024 | O sistema deve permitir consulta de candidaturas por talento e empresa. | Proposta | UC-024 |
| RF-025 | Regras de elegibilidade (mínimas) devem ser validadas antes de criar candidatura. | Pendente | UC-025 |

## 4. S4 — Notificações

| ID | Requisito | Status | Caso de uso |
|---|---|---|---|
| RF-030 | O sistema deve gerar notificações por eventos de negócio (assíncrono). | Proposta | UC-030 |
| RF-031 | O sistema deve entregar notificações por e-mail e/ou plataforma. | Definido (canal) | UC-031 |
| RF-032 | O sistema deve registrar tentativas, estados de entrega e falhas. | Definido | UC-032 |
| RF-033 | O sistema deve reprocessar notificações falhadas conforme política. | Definido (DLQ) | UC-033 |
| RF-034 | O sistema deve permitir consulta de notificações pelo usuário. | Proposta | UC-034 |

## 5. S5 — Indicadores e Dados

| ID | Requisito | Status | Caso de uso |
|---|---|---|---|
| RF-040 | O sistema deve coletar indicadores de utilização da plataforma. | Definido (contexto) | UC-040 |
| RF-041 | O sistema deve expor métricas operacionais e de negócio. | Definido (contexto) | UC-041 |
| RF-042 | O sistema deve manter séries temporais e métricas de impacto (requisitos a confirmar). | Pendente | UC-042 |

## 6. S6 — Comunicação em Tempo Real

| ID | Requisito | Status | Caso de uso |
|---|---|---|---|
| RF-050 | O sistema deve suportar comunicação em tempo real entre empresas e talentos, se incluído no escopo. | Pendente (escopo) | UC-050 |
| RF-051 | Mensagens devem ser persistidas e recuperáveis (se escopo confirmado). | Pendente | UC-051 |
| RF-052 | Reconexão e entrega devem ser suportadas (se escopo confirmado). | Pendente | UC-052 |

## 7. S7 — Identidade e Acesso

| ID | Requisito | Status | Caso de uso |
|---|---|---|---|
| RF-060 | O sistema deve permitir autenticação de usuários e serviços. | Definido (contexto) | UC-060 |
| RF-061 | O sistema deve emitir e validar access tokens e refresh tokens. | Proposto | UC-060 |
| RF-062 | O sistema deve permitir gestão de papéis e permissões. | Definido (contexto) | UC-061 |
| RF-063 | O sistema deve aplicar autorização por recurso. | Proposta | UC-062 |
| RF-064 | O sistema deve permitir revogação de sessões/tokens. | Proposta | UC-063 |

## 8. S8 — Integrações

| ID | Requisito | Status | Caso de uso |
|---|---|---|---|
| RF-070 | O sistema deve receber webhooks de sistemas externos, validar e persistir. | Definido (contexto) | UC-070 |
| RF-071 | O sistema deve deduplicar eventos externos. | Definido (contexto) | UC-071 |
| RF-072 | O sistema deve processar eventos de forma assíncrona. | Proposta | UC-072 |
| RF-073 | O sistema deve permitir envio de eventos/webhooks para sistemas externos (se requerido). | Pendente (direção) | UC-073 |