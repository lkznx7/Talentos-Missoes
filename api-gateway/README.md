# API Gateway

Gateway independente Spring Cloud Gateway reativo. As rotas configuradas são estrutura inicial, com destinos substituíveis por variáveis de ambiente; os serviços não oferecem ainda destinos roteáveis/funcionais.

```bash
mvn spring-boot:run
mvn verify
docker build -t talentos-missoes/api-gateway:local .
```

JDK 25 e Maven 3.6.3+. Porta padrão 8080; timeouts e oito destinos documentados em `.env.example`. Roteamento não validado contra os serviços.
