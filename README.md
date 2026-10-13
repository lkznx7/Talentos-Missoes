# Talentos & Missões

Monorepositório de setup estrutural. A documentação arquitetural fica em [`docs/`](docs/README.md); os ADRs estão em `docs/adr/`. As propostas arquiteturais ainda não foram aprovadas. A estrutura de oito aplicações independentes atende ao escopo de preparação solicitado e mantém registrada a divergência com a recomendação atual de monólito modular no ADR-001.

## Componentes

- `frontend/`: Next.js App Router, TypeScript e npm.
- `api-gateway/`: Spring Cloud Gateway reativo.
- `services/`: oito aplicações Spring Boot independentes.
- `infrastructure/compose/`: PostgreSQL para desenvolvimento local.
- `.github/workflows/ci.yml`: verificações de frontend e Java.

## Pré-requisitos

- Node.js 20.9+ e npm.
- JDK 25 e Maven 3.6.3+.
- Docker com Docker Compose v2.

## Frontend

```bash
cd frontend
npm install
npm run dev
```

Lint, tipos e build: `npm run lint`, `npm run typecheck`, `npm run build`.

## Microsserviços

Execute em cada diretório de `services/`:

```bash
mvn spring-boot:run
mvn verify
```

Exemplo: `cd services/talentos-service`. Porta de execução configurável por `SERVER_PORT` (padrão 8080); Actuator health em `/actuator/health`.

## Gateway

```bash
cd api-gateway
mvn spring-boot:run
mvn verify
```

As rotas e destinos iniciais constam de `api-gateway/src/main/resources/application.yml`; destinos e timeouts são configuráveis via ambiente. Upstreams não estão ativos/validados neste setup.

## Infraestrutura local

Copie `.env.example` para `.env` na raiz quando quiser personalizar as variáveis; o Compose também usa valores locais demonstrativos por padrão.

```bash
docker compose -f infrastructure/compose/docker-compose.yml up -d postgres
docker compose -f infrastructure/compose/docker-compose.yml logs -f postgres
docker compose -f infrastructure/compose/docker-compose.yml down
```

Se criar um `.env` a partir do exemplo para personalizar valores, adicione `--env-file .env` antes de `-f` nos comandos acima.

O volume `postgres-data` persiste dados locais. Para apagar **somente os dados deste ambiente de desenvolvimento**, use `docker compose -f infrastructure/compose/docker-compose.yml down -v`; isso remove o volume nomeado do projeto Compose. Não use esse comando para ambientes com dados que deseja preservar.

PostgreSQL publica a porta 5432 (`POSTGRES_PORT`); usuário, senha e database de exemplo estão em `.env.example` e não são credenciais para outros ambientes. RabbitMQ e Redis não foram incluídos porque continuam pendentes/condicionais nos ADRs.

## Docker

Cada projeto possui seu Dockerfile. Construa a partir da pasta de cada aplicação, por exemplo:

```bash
docker build -t talentos-missoes/talentos-service:local services/talentos-service
docker build -t talentos-missoes/api-gateway:local api-gateway
docker build -t talentos-missoes/frontend:local frontend
```

## CI e decisões pendentes

GitHub Actions executa lint, verificação de tipos e build do frontend; e `mvn verify` para cada aplicação Java com JDK 25. O lockfile do npm ainda deve ser gerado/versionado quando o registry estiver acessível. Consulte [`docs/adr/ADR-016-setup-estrutural.md`](docs/adr/ADR-016-setup-estrutural.md) para as propostas do setup e limitações. É necessário resolver formalmente a divergência do ADR-001 antes de considerar a topologia de implantação aprovada.
