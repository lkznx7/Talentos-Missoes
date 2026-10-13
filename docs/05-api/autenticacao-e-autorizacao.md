# Autenticação e Autorização (API)

## 1. Mecanismo (proposta)
- `Authorization: Bearer <accessToken>` em todas as APIs protegidas.
- Access token JWT assinado (RS256 ou EdDSA), TTL curto (ex.: 15 min hipótese).
- Refresh token rotativo, TTL maior (ex.: 7 dias hipótese), armazenado com segurança.
- Webhooks inbound usam assinatura, não Bearer (ver S8).

## 2. Fluxos
- `POST /v1/auth/login`
- `POST /v1/auth/refresh`
- `POST /v1/auth/logout`
- `GET /v1/auth/me`

## 3. Autorização por recurso
- Papéis iniciais propostos: `talent`, `company_admin`, `company_member`, `platform_operator`, `service`.
- Permissões granulares propostas: `profile:read/write`, `mission:read/write/publish`, `application:read/create/transition`, `notification:read`, `metrics:read`, `chat:read/write`.
- Empresa só acessa seus próprios dados e candidaturas de suas missões (pendente multi-empresa).
- Talento acessa próprios dados e missões públicas.

## 4. Erros de auth
- 401 para token ausente/inválido/expirado/refresh inválido.
- 403 para falta de permissão ou acesso a recurso de terceiro.