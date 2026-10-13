# Decisões Pendentes

## Produto
- Etapas do funil de seleção (regras de estado).
- Limites de candidaturas.
- Política de reabertura/edição de missão.
- Papéis exatos e permissões.
- Retenção de dados.
- Formato de portfólio.
- Regras de comunicação em tempo real.
- Direção de integrações outbound.

## Arquitetura
- Monólito modular (ADR-001) vs 8 microsserviços Java + Gateway (proposta nova).
- Organização de repositório (monorepo vs multirepo) (pendente).
- Broker exato (RabbitMQ/Kafka/SQS) pendente.
- Cache exato (Redis) condicional.
- IdP externo vs serviço próprio.

## Tecnologia
- Versões Spring Boot/Next.js confirmadas na data de implementação.
- Node/React versions.
- Flyway/Postgres driver compatibilidade.

## Operação
- SLA/SLO/RPS/latência a definir.
- Retenção, backup RPO/RTO.
- Roteamento SSE/WebSocket no Gateway.