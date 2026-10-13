# Arquitetura Backend — Java 25

## 1. Versões propostas (verificadas em 2026-10-13)
- **Java:** 25.
- **Spring Boot:** 4.1.x (compatível Java 17–26; OSS support até jul/2027).
- **Spring Cloud (Gateway):** 2025.1.x (compatível Boot 4.0/4.1).
- **Banco:** PostgreSQL (versão a validar no ambiente), Flyway compatível.
- **Broker:** a decidir (RabbitMQ ou Kafka); decisão deve ser ADR.
- **Cache:** Redis apenas onde necessário.

## 2. Estrutura proposta por serviço
Usar organização por domínio:
```
service/
├── src/main/java/com/talentosemissoes/<servico>/
│   ├── Application.java
│   ├── domain/                # regras, entidades, value objects (sem Spring)
│   ├── application/           # use cases, ports, DTOs de aplicação
│   ├── infrastructure/        # JPA, messaging, clients, config
│   └── interfaces/rest/       # controllers, request/response DTOs
├── src/main/resources/db/migration/
├── src/test/
├── Dockerfile
├── pom.xml
└── README.md
```
DI por construtor; sem Lombok; código explícito.

## 3. Regras de fronteira
- Domínio não depende de Spring/JPA/messaging.
- Persistência por serviço ou schema.
- Comunicação entre serviços: preferir eventos; sync com justificativa.
- Transações locais por serviço.

## 4. Matriz de dependências (proposta)
| Serviço | Web | Persistência | Segurança | Mensageria | Cache | Observabilidade |
|---|---|---|---|---|---|---|
| talentos-service | Spring MVC | JPA Postgres | Resource Server | Producer/Consumer se necessário | não inicialmente | Actuator + Micrometer |
| empresas-missoes-service | Spring MVC | JPA Postgres | Resource Server | Producer de mission.* | não | Actuator |
| candidaturas-service | Spring MVC | JPA Postgres | Resource Server | Consumer/Producer | não | Actuator |
| notificacoes-service | Spring MVC (interna/admin) | JPA Postgres | Resource Server | Consumer, producer DLQ | Redis (opcional) | Actuator |
| indicadores-service | Spring MVC | JPA/TSDB? | Resource Server | Consumer | cache de leitura opcional | Actuator |
| comunicacao-service | WebSocket/SSE | JPA | Resource Server | broker pub/sub se multi-instância | Redis pub/sub se necessário | Actuator |
| identidade-service | Spring MVC | JPA Postgres | OAuth2 Authorization Server ou integração IdP | Producer identity.* | não inicial | Actuator |
| integracoes-service | Spring MVC (webhooks) | JPA Postgres | validação assinatura | Producer/consumer | não | Actuator |
| api-gateway | Spring Cloud Gateway (reactive WebFlux) | não | JWT validation | não | Redis opcional rate-limit | Actuator + Micrometer |

## 5. Cada serviço (papel)
- `talentos-service`: perfil, skills, experiências, portfólio.
- `empresas-missoes-service`: empresas, membros, missões, publicação.
- `candidaturas-service`: candidaturas, estados, histórico, dedup.
- `notificacoes-service`: notificações, tentativas, retry, DLQ.
- `indicadores-service`: coleta, agregação, leitura.
- `comunicacao-service`: chat, persistência, entrega, reconexão (escopo pendente).
- `identidade-service`: identidade, tokens, papéis/permissões; integração com IdP externo a decidir.
- `integracoes-service`: webhooks inbound/outbound, validação, dedup, processamento.

## 6. Dependências Maven (exemplos)
- `spring-boot-starter-web` (MVC) para serviços síncronos; **WebFlux** apenas gateway.
- `spring-boot-starter-validation`.
- `spring-boot-starter-security`, `spring-security-oauth2-resource-server` onde precisar validar JWT.
- `spring-boot-starter-data-jpa`, PostgreSQL driver, Flyway.
- `spring-boot-starter-actuator`, Micrometer/OTLP.
- Mensageria conforme broker escolhido.
- `springdoc-openapi` (compatível Boot 4) para docs (pendente versão).
- Testes: `spring-boot-starter-test`, `testcontainers`.

## 7. Proposta de status dos serviços
- `identidade-service` pode inicialmente delegar a IdP externo; decisão ADR.
- `comunicacao-service` depende de S6 confirmado.
- `indicadores-service` pode iniciar como módulo de leitura/projeção.

## 8. Riscos de compatibilidade
- Java 25 + Boot 4.x + Spring Cloud 2025.1.x: verificar versão exata antes de build.
- Gateway reactive vs MVC: não misturar sem necessidade.
- springdoc deve estar compatível com Boot 4/Jakarta EE 11.>