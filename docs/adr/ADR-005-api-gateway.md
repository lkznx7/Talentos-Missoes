# ADR-005 — API Gateway

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-003, ADR-009

## 1. Contexto
Frontend não deve acessar diretamente microsserviços; necessário ponto de entrada único.

## 2. Forças e requisitos
Roteamento, segurança, rate limit, tracing, realtime.

## 3. Alternativas consideradas
A) Spring Cloud Gateway reactive. B) Gateway de infraestrutura (Envoy/Kong/NGINX).

## 4. Decisão
Proposta: **Spring Cloud Gateway (variant reativa)**, com ingress/infra à frente se necessário.

## 5. Justificativa
Integração com ecossistema Spring e configuração em aplicação.

## 6. Consequências
### Positivas
Prototipagem rápida e mais fácil de testar.
### Negativas
Carga extra no time Java; limites se alto tráfego.
### Riscos
Tecnologia reativa misturada com MVC nos serviços (boundary clara).

## 7. Impactos
Rotas, auth, realtime, observabilidade.

## 8. Critérios de validação
Testes de roteamento; latência sob carga.

## 9. Condições de revisão
Se performance operacional exigir gateway de infraestrutura.

## 10. Referências
`docs/03-arquitetura/api-gateway.md`