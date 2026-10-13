# Convenções de API

## 1. Base URL e versionamento
- Base: `https://api.talentos-missoes.example/v1` (exemplo).
- Versionamento por caminho: `/v1`, `/v2`. Mudanças incompatíveis exigem nova versão.

## 2. Formato
- Conteúdo: `application/json; charset=utf-8`.
- Datas: ISO-8601 UTC (RFC 3339).
- IDs: UUIDv4 (string), salvo decisão contrária.
- Paginação: cursor-based proposta (alternativa offset), com `limit` e `cursor`.
- Ordenação: `sort=field:asc|desc`.
- Filtros: query params explícitos por recurso.

## 3. Códigos de status
- 200 OK, 201 Created, 202 Accepted, 204 No Content.
- 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 422 Unprocessable Entity, 429 Too Many Requests.
- 500 Internal Server Error, 503 Service Unavailable.

## 4. Idempotência
- Endpoints de criação suportam `Idempotency-Key` (UUID) quando aplicável.
- Servidor armazena resultado por chave por período (proposto) e retorna mesmo resultado.

## 5. Erros
- Formato Problem Details (RFC 7807) com extensões (`correlationId`, `traceId`).
- Ver `erros.md`.

## 6. Segurança
- `Authorization: Bearer <accessToken>`.
- Escopos/permissões por operação listados em `autenticacao-e-autorizacao.md`.
- Rate limiting proposto (limites a validar).

## 7. Timeouts
- Clientes devem usar timeouts; servidor cancela operações longas.
- Webhooks retornam 202 rapidamente.