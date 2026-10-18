# Auth Service — responsabilidades

## Finalidade

Manter as contas de acesso e autenticar usuários da plataforma. Projeto: `services/Auth-Service/`.

## Responsabilidades

- Cadastrar a conta usada para acessar a plataforma.
- Codificar a senha antes da persistência e verificar credenciais no login.
- Gerar e verificar tokens de acesso.
- Carregar o usuário autenticado e suas authorities no contexto de segurança do serviço.
- Manter os dados de acesso e o papel atribuído à conta.

## Dados próprios

`UserAuth`: UUID, username, hash da senha, datas, papel e campo `revoked`. Os papéis atuais são `USER` e `EMPRESA`; o cadastro atribui `USER`. A classe `Session` existe, mas não possui persistência ou uso no fluxo encontrado.

## Integrações

| Participante | Relação |
|---|---|
| Frontend | Envia cadastro/login e precisa receber o resultado da autenticação |
| Gateway | Deve encaminhar os caminhos públicos para o serviço atual |
| Serviços de negócio | Precisam reconhecer o ator autenticado e relacioná-lo ao proprietário dos recursos |

O JWT atual usa `username` como subject. A associação desse valor ao UUID usado nos modelos de negócio ainda precisa ser definida. A validação de tokens nos demais serviços não é instalada automaticamente pela existência do Auth Service.

## Limites

Perfil profissional pertence a Talentos; cadastro e membros da empresa pertencem a Empresas e Missões. O Auth Service não decide elegibilidade de candidatura nem se um usuário pode alterar uma missão específica. Essas decisões dependem das regras e dos dados do serviço de negócio.

## Estado atual e pendências

Há código de cadastro com BCrypt, login, geração/verificação de JWT e filtro bearer. O login gera o token, mas retorna `200` com corpo vazio. As anotações de validação dos DTOs não são acionadas por `@Valid` no controller. O campo `revoked` não produz bloqueio efetivo nos fluxos encontrados.

Refresh, logout, recuperação de senha, gestão de papéis e sessões persistidas não foram encontrados; sua inclusão depende de definição. Também faltam alinhamento de porta/caminho no Gateway e inclusão na CI.

## Verificação esperada

Cadastro deve persistir hash e conta única; login deve entregar o token após concluir o contrato; acesso protegido deve rejeitar credenciais ou tokens inválidos. Testar a integração pelo Gateway e a política de bloqueio quando implementada.



## API atual

| Método e caminho | Comportamento do controller |
|---|---|
| `POST /auth/register` | Codifica senha, salva usuário e retorna `200` sem corpo |
| `POST /auth/login` | Autentica, gera JWT em variável local e retorna `200` sem corpo |

Ambas recebem `username` e `password`. Os DTOs anotam username como e-mail não vazio e senha entre 6 e 20 caracteres, mas o controller não usa `@Valid`. Duplicidade e outras falhas não possuem contrato de erro específico encontrado. O token não é devolvido por corpo, header ou cookie no login atual.

## Modelo e segurança observados

| Campo de UserAuth | Característica |
|---|---|
| `id` | UUID atribuído no construtor; chave primária |
| `username` | Único, obrigatório, coluna com tamanho 100 |
| `password` | Hash BCrypt, obrigatório, coluna com tamanho 255 |
| `createdAt` / `updatedAt` | Datas de criação/atualização; atualização com `@UpdateTimestamp` |
| `role` | Enum textual USER ou EMPRESA; cadastro define USER |
| `revoked` | Booleano inicialmente false; não aplicado como bloqueio nos fluxos encontrados |

`UserAuth` implementa `UserDetails` e produz `ROLE_USER` ou `ROLE_EMPRESA`. O repositório consulta por username. Não há nome/schema físico fixado por `@Table`.

O JWT contém subject com username, expiração e claim `ROLES_`. A chave é construída a partir de segredo Base64 com `HmacSHA256`; `signWith` seleciona a assinatura. O código não define issuer, audience, `jti` ou `iat`. O filtro verifica o bearer, carrega o usuário pelo subject e usa as authorities atuais do banco, sem usar a claim de papéis para construir o contexto.

A segurança libera `/auth/**`, exige autenticação nas demais rotas, usa política stateless e desabilita CSRF. O filtro participa da cadeia antes do filtro de username/password e tem registro independente no servlet container desativado. Erros JWT tratados limpam o contexto e deixam a cadeia continuar; não há corpo JSON de erro escrito pelo filtro.

`Session` é uma classe sem mapeamento JPA com ID, token, IP e datas; não foi encontrado fluxo que a utilize. `IpAdressService` obtém o endereço remoto, mas não é usado no fluxo mostrado. Essas classes não comprovam controle de sessões ou revogação.

## Executar e integrar

O POM declara Java 21, Spring Boot 4.1.1, JJWT 0.12.6, JPA e H2 em runtime. Usa Maven Wrapper; não há Dockerfile encontrado para o serviço. Na raiz:

```powershell
cd services/Auth-Service
.\mvnw.cmd verify
.\mvnw.cmd spring-boot:run
```

A porta declarada é 8081 e conflita com Talentos. Para executar juntos, escolha uma porta livre na sessão, por exemplo `$env:SERVER_PORT = '8087'`. Ajustar a porta não resolve sozinho a diferença entre `/auth/**` e `/v1/auth/**` no Gateway.

`JWT_SECRET` configura o segredo Base64; o código tem um fallback que não é reproduzido aqui. Use um segredo próprio. `JWT_EXPIRATION` tem padrão de 7200 segundos. H2 está no POM, mas não há datasource PostgreSQL explícito; definir persistência durável e migrations antes de uso real. Actuator é protegido pela regra geral da segurança.

A CI ainda aponta para o projeto antigo e precisa incluir o Auth Service atual. O teste encontrado é de contexto; cadastro, login, validação, expiração, acesso protegido e fluxo via Gateway exigem testes próprios.

## Fontes

Descrito por inspeção, sem execução dos fluxos nesta revisão: [controller](../../services/Auth-Service/src/main/java/com/lkznx7/authservice/controller/AuthController.java), [segurança](../../services/Auth-Service/src/main/java/com/lkznx7/authservice/config/JwtConfig.java), [JWT](../../services/Auth-Service/src/main/java/com/lkznx7/authservice/service/JwtServiceImpl.java), [filtro](../../services/Auth-Service/src/main/java/com/lkznx7/authservice/filter/JwtAuthenticationFilter.java), [entidade](../../services/Auth-Service/src/main/java/com/lkznx7/authservice/entity/UserAuth.java) e [POM](../../services/Auth-Service/pom.xml).

Consulte também as [regras comuns](../arquitetura.md), o [guia local](../desenvolvimento-local.md) e as [pendências gerais](../pendencias.md).

## Contrato inicial e canais de comunicação

Para as cinco entidades do núcleo, consultar o [modelo oficial por entidade](../modelo-dados-oficial.md). REST recebe comandos; gRPC atende consultas internas remotas; GraphQL compõe leituras no BFF. Notificações usa fila e SSE, Integrações usa fila e webhooks, e Comunicação usa WebSocket para chat. Dados operacionais desses três serviços e de Indicadores não estão nas cinco tabelas e exigem evolução antes da implementação. Detalhes, etapas e falhas estão no [guia de comunicação](../comunicacao-entidades.md).
