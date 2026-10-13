# Critérios de Aceite

Convenção de IDs de testes sugerida: `TC-XXX` (caso de teste). Cada critério deve ser verificável. Métricas numéricas são hipóteses até validação.

## 1. Por requisito-chave

### RF-020 / RF-021 — Candidatura

- **TC-020-1:** Dado talento autenticado e missão publicada e elegível, quando submeter candidatura, então retorna 201 Created com `applicationId` e estado inicial.
- **TC-021-1:** Dado segunda candidatura do mesmo talento à mesma missão, quando submeter, então retorna 409 Conflict e não cria novo registro.
- **TC-021-2:** Sob concorrência, tentativas simultâneas resultam em exatamente uma candidatura (teste de concorrência).

### RF-031–033 — Notificações

- **TC-031-1:** Evento de negócio elegível enfileira notificação; estado inicial `queued`.
- **TC-032-1:** Tentativa de entrega registra `attempted_at`, `status` e `failure_reason` se falhar.
- **TC-033-1:** Notificação excedendo retentativas vai para DLQ com estado `dead_lettered`.
- **TC-033-2:** Reprocessamento manual muda estado para `queued` sem duplicar efeitos.

### RF-060 — Autenticação

- **TC-060-1:** Credenciais válidas retornam `access_token` e `refresh_token`.
- **TC-060-2:** Token expirado retorna 401 com código `auth.token_expired`.
- **TC-060-3:** Refresh válido renova access token; refresh inválido retorna 401.

### RF-070/071 — Webhooks

- **TC-070-1:** Webhook com assinatura válida é persistido e retorna 202 Accepted.
- **TC-070-2:** Webhook com assinatura inválida retorna 401/403 e é registrado como rejeitado.
- **TC-071-1:** Reentrega do mesmo `eventId` não gera processamento duplicado (idempotente).

## 2. Qualidade geral

- Contratos de API válidos (OpenAPI parseável).
- Diagramas Mermaid renderizáveis.
- DBML parseável em dbdiagram.io.
- Cada requisito crítico possui pelo menos um TC.

## 3. Critérios de aceite do MVP técnico (proposto)

1. CRUD básico de perfil/empresa/missão com autenticação.
2. Candidatura com prevenção de duplicidade.
3. Worker de notificação funcional com DLQ.
4. Pipeline de webhook inbound com validação e deduplicação.
5. Métricas básicas expostas (se S5 incluído).
6. Comunicação em tempo real apenas se escopo confirmado.

## 4. Pendências

- Critérios detalhados por RF dependem de regras de negócio não definidas (funil, elegibilidade, limites).
- Valores de performance serão estabelecidos por testes de carga.