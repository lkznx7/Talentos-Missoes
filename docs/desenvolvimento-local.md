# Desenvolvimento local

Execute os comandos a partir da raiz, salvo indicação. O roteiro verifica a base estrutural; não demonstra funcionalidades completas do produto.

## Pré-requisitos

JDK 25 para os serviços estruturais (Java 21 para Auth Service), Maven 3.6.3+, Node.js 22, npm e Docker com Compose v2. Verifique `java -version`, `mvn -version`, `node --version`, `npm --version` e `docker compose version`. As versões dos frameworks estão nos manifestos; não há verificação de disponibilidade em registry nesta revisão documental.

## PostgreSQL

```powershell
docker compose -f infrastructure/compose/docker-compose.yml up -d postgres
docker compose -f infrastructure/compose/docker-compose.yml ps
docker compose -f infrastructure/compose/docker-compose.yml logs -f postgres
```

Padrões de desenvolvimento do Compose: database `talentos_missoes`, usuário/senha `tm_dev`, porta `5432`. O healthcheck usa `pg_isready`. O volume `postgres-data` mantém os dados entre reinícios. Essas credenciais são exemplos locais.

Para personalizar, crie `.env` na raiz com as chaves `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD` e `POSTGRES_PORT`, e execute:

```powershell
docker compose --env-file .env -f infrastructure/compose/docker-compose.yml up -d postgres
```

O `.env.example` da raiz está vazio. Alterar credenciais no Compose não altera um banco já inicializado no volume; faça a alteração no banco ou use um ambiente novo.

Para parar sem apagar os dados:

```powershell
docker compose -f infrastructure/compose/docker-compose.yml down
```

`down -v` remove os volumes desse projeto Compose e apaga os dados locais. Use somente quando a exclusão dos dados for intencional.

## Aplicações Java

Cada aplicação é independente; não há POM agregador na raiz. Exemplo:

```powershell
cd services/talentos-service
mvn spring-boot:run
```

| Aplicação documentada | Porta padrão | HTTP/health na base |
|---|---|---|
| Gateway | 8080 | `/actuator/health` |
| Talentos | 8081 | `/actuator/health` |
| Empresas e Missões | 8082 | `/actuator/health` |
| Candidaturas | 8083 | `/actuator/health` |
| Notificações | 8084 | `/actuator/health` |
| Indicadores | 8085 | `/actuator/health` |
| Comunicação | 8086 configurada | Sem starter web; não pressupor endpoint HTTP |
| Integrações | 8088 | `/actuator/health` |

As portas vêm dos respectivos `application.yml`. Para sobrescrever no PowerShell: `$env:SERVER_PORT = '8091'`; execute o Maven na mesma sessão. Ajuste também a variável de destino no terminal do Gateway. Para limpar a sobrescrita: `Remove-Item Env:SERVER_PORT`.

Subir PostgreSQL não configura datasource nos serviços estruturais. Adicionar driver, persistência, migrations e variáveis é trabalho de implementação.

## Frontend

```powershell
cd frontend
npm install
npm run dev
```

Acesse `http://localhost:3000`. Não há lockfile versionado; a instalação atual não é reproduzível por lockfile. Exemplos de URL estão em `frontend/.env.example`; confirme o uso no código antes de atribuir efeito a uma variável.

## Verificações e Docker

No frontend: `npm run lint`, `npm run typecheck`, `npm run build`. Em cada projeto Java documentado: `mvn verify`. Testes de contexto não comprovam persistência, roteamento ou regras de negócio.

Na raiz, exemplos de build das imagens:

```powershell
docker build -t talentos-missoes/talentos-service:local services/talentos-service
docker build -t talentos-missoes/api-gateway:local api-gateway
docker build -t talentos-missoes/frontend:local frontend
```

Esses comandos constroem imagens separadas. O Compose local não sobe as aplicações. Dentro de containers, `localhost` aponta para o próprio container; os destinos do Gateway precisam usar nomes de rede ou endereços apropriados.

## Diagnóstico rápido

| Sintoma | Verificar |
|---|---|
| Maven acusa versão Java incompatível | JDK usado por `mvn -version` e `JAVA_HOME` |
| Porta ocupada | Processo ativo ou `SERVER_PORT`; destino correspondente no Gateway |
| Gateway não alcança serviço | Health direto do destino, URL e disponibilidade de rede |
| API retorna 404 | Controller implementado, caminho `/v1` e predicado da rota |
| Comunicação não responde por HTTP | Starter web ausente na base atual |
| Banco não aceita novas credenciais | Credenciais do volume inicializado e porta publicada |
| Frontend abre mas não executa o fluxo | Tela e integração ainda não implementadas |


## Auth Service

Segue um roteiro próprio com Java 21 e Maven Wrapper. A porta declarada 8081 conflita com Talentos; o Actuator é protegido pela configuração de segurança. Consulte [execução e integração](microsservicos/auth-service.md) antes de executar os serviços juntos.
