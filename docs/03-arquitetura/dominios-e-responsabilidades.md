# Domínios e Responsabilidades

## 1. Matriz de responsabilidades

| Domínio | Responsabilidades (faz) | Não faz / limites | Dados sob autoridade | Dependências | Interfaces públicas | Eventos publicados | Eventos consumidos |
|---|---|---|---|---|---|---|---|
| S1 Talentos | Perfis, habilidades, experiências, portfólios | Não cria candidaturas; não autentica | `talent`, `skill`, `experience`, `portfolio_item` | S7 (identidade) | API interna/HTTP perfil | `talent.profile.*` | `identity.user.*` (proposto) |
| S2 Empresas e Missões | Empresa, missão, requisitos, prazos, estados de publicação | Não gerencia candidaturas | `company`, `mission` | S7 | API missão | `mission.*` | - |
| S3 Candidaturas | Candidatura, estado, histórico, elegibilidade/dedup | Não altera perfis/missões | `application`, `application_history` | S1, S2, S7 | API candidatura | `application.*` | `mission.*`, `talent.profile.*` |
| S4 Notificações | Notificação, tentativas, estados, DLQ, reprocessamento | Não define conteúdo de negócio | `notification`, `notification_attempt`, DLQ | Broker, e-mail provider | API notificação | `notification.*` | Eventos de domínio (`*.created` etc.) |
| S5 Indicadores e Dados | Métricas, séries temporais, impacto | Não altera domínios fonte; leitura/materialização | `metric_*`, `timeseries_*` | Broker | API de métricas | `metrics.*` (opcional) | Eventos de domínio |
| S6 Comunicação em Tempo Real | Mensagens, canais, entrega, reconexão, histórico (se escopo) | Não armazena candidaturas | `conversation`, `message` | S7, S3 (pendente) | WS/SSE | `message.*` (proposto) | `application.*` (pendente) |
| S7 Identidade e Acesso | Identidades, credenciais, papéis, permissões, tokens, sessões | Não armazena perfil de negócio | `user`, `role`, `permission`, `session`, `refresh_token` | IdP externo (pendente) | API auth | `identity.user.*` | - |
| S8 Integrações | Webhooks inbound/outbound, validação, persistência, dedup, processamento | Não regra de negócio de domínios | `external_event`, `webhook_delivery` | Broker | Webhook endpoints | `integration.*` | `*.created/updated` |

## 2. Sobreposições e decisões de fronteira

- **Empresa vs Perfil de usuário:** S2 mantém dados da empresa; S7 mantém identidades e credenciais. Um usuário pode estar vinculado a uma empresa (relacionamento, não tabela compartilhada). Pendente política de multi-empresa.
- **Talento vs Identidade:** S1 mantém perfil profissional; S7 mantém credencial. O vínculo é `user_id` referenciado, não chave estrangeira física entre bancos se separados.
- **Notificações vs negócio:** domínios publicam eventos; S4 decide formato, canal e retry. Domínios não chamam S4 sincronicamente (proposto).
- **Indicadores:** somente leitura e materialização; nunca altera dados transacionais.

## 3. Regras de interação entre domínios

1. Sem alteração direta em tabelas de outro domínio (ADR-004).
2. Interações síncronas somente quando necessário e documentado (ex.: validar existência).
3. Interações assíncronas por eventos para efeitos colaterais.
4. Cada domínio valida suas próprias invariantes.<|end|>