# api-gateway

## Árvore
```
api-gateway/
├── src/main/java/com/talentosemissoes/gateway/
├── src/main/resources/application.yml
├── src/test/java/
├── Dockerfile
└── pom.xml
```

## Responsabilidade
- Roteamento, auth de token na borda, rate limit, CORS, tracing.
- Não autorização por recurso nem regra de negócio.

## Dependências (propostas)
- spring-boot-starter (4.1.x)
- spring-cloud-starter-gateway-server-webflux (2025.1.x)
- spring-boot-starter-actuator
- spring-boot-starter-security + resource server (se validar JWT)
- redis (opcional para rate limit)