# Atores e Sistemas Externos

## 1. Atores humanos

| Ator | Descrição | Relação com domínios | Permissões típicas (pendente) |
|---|---|---|---|
| Talento | Pessoa profissional que mantém perfil e se candidata | S1, S3, S6, S7 | `profile:*`, `application:create`, `chat:send` |
| Empresa (admin) | Representante da empresa que gerencia empresa e missões | S2, S3, S6, S7 | `company:*`, `mission:*`, `application:read`, `chat:send` |
| Operador da plataforma | Equipe interna que opera suporte, moderação e reprocessamento | Todos | alto privilégio, auditado |
| Desenvolvedor/Engenheiro | Equipe técnica | Infra, operação | acesso técnico restrito |

## 2. Sistemas externos

| Sistema | Domínio afetado | Tipo de integração | Status |
|---|---|---|---|
| Provedor de Identidade / OIDC (a confirmar) | S7 | OIDC/OAuth2 (proposta) | Pendente |
| Provedor de e-mail transacional (a confirmar) | S4 | API HTTP + webhook de status (proposta) | Pendente |
| Sistemas de parceiros via webhook (a confirmar) | S8 | Webhooks inbound/outbound | Pendente |
| Observabilidade (a confirmar) | Todos | Telemetria (OTLP proposto) | Pendente |

## 3. Fronteiras de segurança

- Toda requisição humana é autenticada pelo domínio S7 ou pelo IdP externo, resultando em um `access token`.
- Integrações máquina-a-máquina usam segredos/armazenamento seguro e, quando externas, validação de assinatura por webhook (HMAC pendente de algoritmo).
- A autorização é verificada **por recurso**, não apenas por papel (ver `08-seguranca/autorizacao.md`).
- Operadores da plataforma não contornam as políticas de autorização sem trilha de auditoria.

## 4. Atores não humanos (sistemas internos)

| Ator | Domínio | Descrição |
|---|---|---|
| Worker de notificações | S4 | Consome eventos e envia notificações, registra estados |
| Worker de integrações | S8 | Processa webhooks e eventos externos |
| Worker de indicadores | S5 | Agrega métricas e materializa leituras |
| Broker de mensagens | Infra | Transporte de eventos (tecnologia pendente) |