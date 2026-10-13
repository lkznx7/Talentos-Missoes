# Indicadores Service

Aplicação Spring Boot independente; contém somente inicialização, Actuator e teste de contexto. Não há endpoints ou regras de negócio.

## Requisitos

- JDK 25 e Maven 3.6.3+.

## Comandos

```bash
mvn spring-boot:run
mvn verify
docker build -t indicadores-service:local .
```

Porta padrão: 8080 (`SERVER_PORT` configurável). Health check: `/actuator/health`.

As dependências de domínio, banco, segurança e mensageria ficam fora deste esqueleto até haver necessidade definida e aprovação das propostas arquiteturais correspondentes.
