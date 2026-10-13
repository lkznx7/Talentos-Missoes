# Casos de Uso

Convenção `UC-XXX`. Atores referem-se a `01-visao-geral/atores-e-sistemas-externos.md`. Pré/pós-condições podem mudar conforme regras de negócio confirmadas.

## 1. S1 — Talentos

| ID | Caso de uso | Ator principal | Descrição resumida |
|---|---|---|---|
| UC-001 | Criar/editar perfil | Talento | Cria/atualiza dados básicos do perfil profissional |
| UC-002 | Gerenciar habilidades | Talento | Adiciona/remove habilidades associadas ao perfil |
| UC-003 | Gerenciar experiências | Talento | Adiciona/remove experiências profissionais |
| UC-004 | Gerenciar portfólio | Talento | Adiciona/remove itens de portfólio |
| UC-005 | Consultar próprio perfil | Talento | Recupera dados completos do próprio perfil |
| UC-006 | Consultar perfil de candidato | Empresa | Recupera perfil de talento com autorização e escopo |

## 2. S2 — Empresas e Missões

| ID | Caso de uso | Ator principal | Descrição resumida |
|---|---|---|---|
| UC-010 | Criar/editar empresa | Empresa admin | Cria/atualiza dados da empresa |
| UC-011 | Criar rascunho de missão | Empresa admin | Cria missão em estado `draft` |
| UC-012 | Publicar/pausar/encerrar missão | Empresa admin | Altera estado de publicação |
| UC-013 | Definir requisitos/prazos | Empresa admin | Define critérios e datas da missão |
| UC-014 | Consultar missões | Talento/Empresa | Lista/obtém missões, filtros/paginação |
| UC-015 | Editar missão com candidaturas | Empresa admin | Alteração condicionada a estado (pendente) |

## 3. S3 — Candidaturas

| ID | Caso de uso | Ator principal | Descrição resumida |
|---|---|---|---|
| UC-020 | Criar candidatura | Talento | Candidata-se a missão elegível (RF-020) |
| UC-021 | Evitar duplicidade | Sistema | Rejeita segunda candidatura (RF-021) |
| UC-022 | Consultar histórico | Talento/Empresa | Lê estado e histórico da candidatura |
| UC-023 | Transicionar estado | Empresa/Sistema | Avança estado do funil (regras pendentes) |
| UC-024 | Listar candidaturas | Empresa/Talento | Lista por missão ou talento |
| UC-025 | Validar elegibilidade | Sistema | Valida pré-condições antes de criar |

## 4. S4 — Notificações

| ID | Caso de uso | Ator principal | Descrição resumida |
|---|---|---|---|
| UC-030 | Gerar notificação | Sistema | Cria notificação a partir de evento |
| UC-031 | Entregar notificação | Worker | Envia por e-mail/plataforma |
| UC-032 | Registrar estado de entrega | Worker | Registra tentativas e falhas |
| UC-033 | Reprocessar notificações | Operador/Worker | Reprocessa DLQ |
| UC-034 | Consultar notificações | Usuário | Lista/inbox de notificações |

## 5. S5 — Indicadores

| ID | Caso de uso | Ator principal | Descrição resumida |
|---|---|---|---|
| UC-040 | Coletar métrica de uso | Sistema | Instrumenta eventos |
| UC-041 | Expor métricas | Sistema/Operador | Endpoints/dashboards |
| UC-042 | Series temporais de impacto | Sistema | Materializa métricas (pendente) |

## 6. S6 — Comunicação em Tempo Real (escopo pendente)

| ID | Caso de uso | Ator principal | Descrição resumida |
|---|---|---|---|
| UC-050 | Enviar mensagem | Empresa/Talento | Envia mensagem em canal |
| UC-051 | Persistir/receber histórico | Sistema | Persiste e recupera mensagens |
| UC-052 | Reconectar e receber pendentes | Cliente | Resume sessão websocket |

## 7. S7 — Identidade e Acesso

| ID | Caso de uso | Ator principal | Descrição resumida |
|---|---|---|---|
| UC-060 | Autenticar | Usuário/Serviço | Login, refresh, logout |
| UC-061 | Gerenciar papéis/permissões | Operador/admin | Define roles |
| UC-062 | Autorizar recurso | Sistema | Verifica escopo/acesso |
| UC-063 | Revogar sessão/token | Usuário/Operador | Invalida sessão |

## 8. S8 — Integrações

| ID | Caso de uso | Ator principal | Descrição resumida |
|---|---|---|---|
| UC-070 | Receber webhook | Sistema externo | Recebe e persiste evento |
| UC-071 | Deduplicar evento | Sistema | Evita processamento duplicado |
| UC-072 | Processar evento | Worker | Processa assincronamente |
| UC-073 | Enviar webhook | Sistema | Entrega eventos externos (pendente direção) |

## 9. Fluxos críticos

- Fluxo de candidatura (UC-020/021/025) → ver `04-diagramas/sequencia-candidatura.mmd`.
- Fluxo de notificação (UC-030/031/032/033).
- Fluxo de webhook inbound (UC-070/071/072).
- Fluxo de autenticação (UC-060).