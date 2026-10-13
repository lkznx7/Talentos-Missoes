# Erros de API

Formato padronizado (Problem Details):

```json
{
  "type": "https://errors.talentos-missoes.example/auth/token_expired",
  "title": "Access token expirado",
  "status": 401,
  "detail": "O access token fornecido expirou.",
  "instance": "/v1/missions",
  "correlationId": "a1b2...",
  "traceId": "0af7...",
  "errorCode": "auth.token_expired"
}
```

## 1. Campos

| Campo | Obrigatório | Descrição |
|---|---|---|
| type | sim | URI da classe de erro |
| title | sim | Resumo curto |
| status | sim | HTTP status |
| detail | não | Explicação contextual |
| instance | não | Caminho da requisição |
| correlationId | sim (servidor) | ID de correlação |
| traceId | não | ID de trace |
| errorCode | sim | Identificador estável para clientes |

## 2. Códigos de erro (exemplos)

| errorCode | Status | Descrição |
|---|---|---|
| `auth.invalid_credentials` | 401 | Credenciais inválidas |
| `auth.token_expired` | 401 | Access token expirado |
| `auth.refresh_invalid` | 401 | Refresh inválido/expirado/revogado |
| `auth.forbidden` | 403 | Sem permissão |
| `resource.not_found` | 404 | Recurso não encontrado |
| `application.duplicate` | 409 | Candidatura duplicada |
| `application.invalid_transition` | 409/422 | Transição de estado inválida |
| `mission.not_published` | 422 | Missão não publicada |
| `validation.invalid` | 422 | Falha de validação (detalhes por campo) |
| `rate_limit.exceeded` | 429 | Limite excedido |
| `system.unavailable` | 503 | Dependência indisponível |

## 3. Validação

Respostas 422 podem incluir `errors: [{ field, message, code }]`.