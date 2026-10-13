# API Gateway

## 1. Tecnologia recomendada (proposta)
- **Spring Cloud Gateway** (variante **reactiva/WebFlux**), integrado ao Spring Boot 4.1.x e Spring Cloud 2025.1.x.
- Assumir WebFlux (reactive) para o Gateway; não usar Spring MVC nele.

## 2. Responsabilidades
- Ponto de entrada único do frontend.
- Roteamento para serviços.
- Validação de token (autenticação) na borda (proposta); autorização por recurso permanece nos serviços.
- Rate limiting, CORS, timeouts.
- Propagação de `correlationId`, `traceparent`.
- Terminação TLS (proposta; pode ser no ingress/duo).

## 3. Rotas propostas
| Prefixo público | Serviço | Auth | Observações |
|---|---|---|---|
| `/v1/auth/**` | identidade-service | misto (login público, me protegido) | rate limit forte |
| `/v1/talents/**` | talentos-service | bearer | authz por recurso |
| `/v1/companies/**` | empresas-missoes-service | bearer | |
| `/v1/missions/**` | empresas-missoes-service | bearer (list pode ser autenticado) | |
| `/v1/missions/{id}/applications` | candidaturas-service | bearer | |
| `/v1/applications/**` | candidaturas-service | bearer | |
| `/v1/notifications/**` | notificacoes-service | bearer | |
| `/v1/metrics/**` | indicadores-service | bearer | |
| `/v1/realtime/**` (WS/SSE) | comunicacao-service | bearer (query/token) | timeout longo/idle |
| `/v1/webhooks/{source}` | integracoes-service | assinatura | sem bearer; validação no serviço |
| `/v1/admin/**` | vários | bearer + role operator | roteamento por subpath |

## 4. Realtime através do Gateway
- WebSocket: habilitar upgrade; sticky session se estado em instância; ou shared state via broker/Redis.
- SSE: streaming sem upgrade; timeouts ajustados; buffering de proxies deve ser desabilitado.
- Auth para WS via `Authorization` header ou token na query (segurança a avaliar, pois query pode vazar).

## 5. Erros
- Padronizar Problem Details.
- Upstream indisponível: 502/503/504 conforme timeout.
- Circuit breaker (proposto) quando adotado.

## 6. Testes
- Testes de roteamento com Spring Cloud Contract ou integration tests (proposto) contra serviços mock.
- Testes de CORS, jwt, rate limit.