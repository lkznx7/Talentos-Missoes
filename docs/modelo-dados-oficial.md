# Modelo de dados oficial — versão inicial

Status: **definido como modelo-alvo inicial**, por aprovação do responsável pelo projeto em 18/10/2026. A aprovação do modelo não significa que o código ou o banco já o implementem.

## Precedência documental

Para a versão inicial, este modelo e o guia de comunicação prevalecem sobre descrições ampliadas dos microsserviços. O modelo define dados; o guia define canais. O código descreve apenas o que já existe, não substitui o contrato-alvo. O [relatório de revisão](revisao-documentacao.md) registra diferenças e limitações.

## Objetivo e fonte oficial

Documentar a versão mais simples do núcleo Talentos & Missões: contas, perfis profissionais, empresas, missões e candidaturas.

O [arquivo DBML oficial](dbml/talentos-missoes.dbml) é a fonte da estrutura, dos tipos, das obrigatoriedades e dos relacionamentos desta versão. Este documento define sua interpretação e os limites de implementação. Mudanças devem atualizar ambos no mesmo commit.

Para visualizar, abra https://dbdiagram.io e cole o conteúdo do DBML em um diagrama. O diagrama descreve a estrutura; não executa migrations nem configura serviços.

Este modelo substitui, para o núcleo inicial, a proposta ampliada de `docs/07-dados/dbml/talentos.dbml` presente no histórico. A arquitetura anterior de oito serviços permanece uma referência de evolução, sem exigir todas as suas tabelas nesta primeira versão. A documentação de responsabilidades dos serviços não comprova sua implementação.

## Decisão de persistência

O alvo deste DBML é **um banco PostgreSQL compartilhado**, com as cinco tabelas no schema padrão `public` e chaves estrangeiras físicas. Os grupos representam responsabilidades lógicas, não bancos separados, schemas ou aplicações já implantadas.

| Grupo no DBML | Responsabilidade | Tabelas |
|---|---|---|
| `identidade_service` | Conta, credenciais e papel | `auth` |
| `talentos_service` | Perfil profissional | `talentos` |
| `empresas_missoes_service` | Empresa e oportunidades | `empresas`, `missoes` |
| `candidaturas_service` | Inscrições e situação da seleção | `candidaturas` |

A implementação pode começar em módulos na mesma aplicação; esta documentação não modifica nem reúne os projetos Spring Boot existentes. Cada domínio controla a escrita de suas tabelas. Separar bancos futuramente exigirá remover FKs entre bancos e definir validação por API/eventos e consistência; não basta alterar os grupos do diagrama.

## Contratos de comunicação

Cada entidade abaixo especifica seus canais de entrada, consultas internas e eventos. São contratos de destino a implementar, não endpoints comprovadamente disponíveis. O [guia de comunicação](comunicacao-entidades.md) define transporte, autenticação, falhas e etapas de adoção dos sete mecanismos.

## Dicionário de dados

Campos sem `not null` no DBML são opcionais. Chaves primárias são obrigatórias. UUIDs são gerados na aplicação, sem default de geração no banco.

### auth — conta de acesso

| Campo | Significado |
|---|---|
| `id` | Identificador UUID da conta |
| `username` | Identificador único de login, obrigatório, até 100 caracteres |
| `password_hash` | Hash da senha, obrigatório, até 255 caracteres; nunca senha em texto |
| `role` | Um único papel: `ADMIN`, `USER` ou `EMPRESA`; padrão `USER` |
| `created_at` | Instante da criação; padrão `now()` |
| `updated_at` | Instante da última alteração; valor inicial `now()` |
| `ativo` | Conta habilitada; padrão `true` |

O cadastro público não deve aceitar elevação para `ADMIN`. A atribuição de `EMPRESA` exige um fluxo controlado. A política de normalização de username deve ser definida antes da implementação: o UNIQUE simples do PostgreSQL não garante equivalência entre maiúsculas e minúsculas.

#### Comunicação de auth

Responsável: Auth Service. REST é a interface externa; gRPC atende verificações internas pontuais. Senha e hash nunca entram em GraphQL, eventos ou canais em tempo real.

| Origem → destino | Tipo | Operação e contrato planejado |
|---|---|---|
| Frontend → Auth, via Gateway | REST/HTTPS, síncrono | `POST /v1/auth/register`, `POST /v1/auth/login`, `GET /v1/auth/me`; cadastro recebe credenciais e login devolve token após autenticação |
| Serviço autorizado → Auth | gRPC unary, síncrono | `AuthQuery.GetAccount(id)` devolve somente `id`, `role`, `ativo`; deadline e autenticação entre serviços obrigatórios |
| Auth → consumidores internos | Fila, assíncrono | `auth.account.created.v1`, `auth.account.deactivated.v1`; publicar apenas identificador e campos necessários |
| BFF GraphQL → Auth | gRPC unary, síncrono | Resolver `me` obtém dados mínimos da conta autenticada |

`PATCH /v1/auth/me` é reservado à edição permitida da própria conta; senha e administração de papéis exigem contratos específicos antes de implementação. Validar JWT localmente nos serviços; não chamar Auth por gRPC em toda requisição. Desativação exige política explícita para tokens ainda válidos. REST inicial não inclui logout ou refresh persistidos.

WebSocket, webhook e SSE não são interfaces diretas desta entidade. Uma conta criada não gera automaticamente os dois tipos de perfil.

### talentos — perfil profissional

| Campo | Significado |
|---|---|
| `id_auth` | PK e FK para `auth.id`; o perfil reutiliza o UUID da conta |
| `nome` | Nome obrigatório, até 100 caracteres |
| `sobrenome` | Sobrenome, até 100 caracteres |
| `sobre` | Apresentação profissional em texto livre |
| `data_nascimento` | Data de nascimento, sem horário |
| `habilidades` | Habilidades em texto livre nesta versão |
| `experiencias` | Experiências em um único texto, sem limite de três registros |
| `link_portfolio` | Endereço do portfólio, até 500 caracteres |

Habilidades e experiências não são tabelas próprias. Isso simplifica o cadastro, mas limita filtros estruturados e a edição individual de experiências.

#### Comunicação de talentos

Responsável: Talentos. O UUID do ator autenticado determina o perfil que pode ser alterado.

| Origem → destino | Tipo | Operação e contrato planejado |
|---|---|---|
| Frontend → Talentos, via Gateway | REST/HTTPS, síncrono | `POST /v1/talents`, `GET /v1/talents/me`, `PATCH /v1/talents/me`; criar, consultar e editar o próprio perfil |
| Candidaturas → Talentos | gRPC unary, síncrono | `TalentQuery.GetTalent(id)` devolve existência e dados mínimos necessários à candidatura |
| BFF GraphQL → Talentos | gRPC unary, síncrono | `TalentQuery.GetTalent(id)` e futuro `GetTalents(ids)` para leitura autorizada e consultas em lote |
| Talentos → consumidores internos | Fila, assíncrono | `talent.profile.created.v1`, `talent.profile.updated.v1`; Indicadores recebe somente dados necessários |

Consulta GraphQL planejada: `talento(id)`; exposição do perfil depende de autorização, não implica perfil público. Data de nascimento e outros dados pessoais não são enviados por padrão aos eventos. Não há WebSocket, SSE ou webhook direto para edição de perfil.

### empresas — perfil empresarial

| Campo | Significado |
|---|---|
| `id_auth` | PK e FK para `auth.id`; reutiliza o UUID da conta empresarial |
| `nome` | Nome obrigatório, até 150 caracteres |
| `descricao` | Apresentação da empresa |
| `site` | Endereço do site, até 500 caracteres |

Uma empresa corresponde a uma conta. Múltiplos funcionários, membros e permissões por empresa ficam fora desta versão.

#### Comunicação de empresas

Responsável: Empresas e Missões, o mesmo serviço que controla `missoes`.

| Origem → destino | Tipo | Operação e contrato planejado |
|---|---|---|
| Frontend → Empresas e Missões, via Gateway | REST/HTTPS, síncrono | `POST /v1/companies`, `GET /v1/companies/me`, `PATCH /v1/companies/me` |
| BFF GraphQL → Empresas e Missões | gRPC unary, síncrono | `CompanyQuery.GetCompany(id)` devolve a empresa; `MissionQuery.ListByCompany(company_id, cursor, limit)` fornece suas missões de forma paginada |
| Empresas e Missões → consumidores internos | Fila, assíncrono | `company.created.v1`, `company.updated.v1` |

Acesso entre empresa e suas missões ocorre dentro do serviço, sem chamada gRPC para si próprio. WebSocket e SSE não são necessários para editar empresa. Importação de cadastro externo por webhook fica para Integrações, com contrato de parceiro ainda a definir.

### missoes — oportunidades

| Campo | Significado |
|---|---|
| `id` | UUID da missão |
| `empresa_id` | FK obrigatória para `empresas.id_auth` |
| `titulo` | Título obrigatório, até 150 caracteres |
| `descricao` | Descrição obrigatória |
| `requisitos` | Requisitos em texto livre |
| `prazo` | Data limite opcional, sem horário |
| `aberta` | Indica se aceita candidaturas; padrão `true` |
| `created_at` | Instante da criação; padrão `now()` |

#### Comunicação de missoes

Responsável: Empresas e Missões. A empresa autenticada só altera missões próprias.

| Origem → destino | Tipo | Operação e contrato planejado |
|---|---|---|
| Frontend → Empresas e Missões, via Gateway | REST/HTTPS, síncrono | `POST /v1/missions`, `GET /v1/missions`, `GET /v1/missions/{id}`, `PATCH /v1/missions/{id}`; `aberta` controla abertura/fechamento |
| Candidaturas → Empresas e Missões | gRPC unary, síncrono | `MissionQuery.GetMissionForApplication(id)` devolve `id`, `empresa_id`, `aberta`, `prazo` |
| BFF GraphQL → Empresas e Missões | gRPC unary, síncrono | `MissionQuery.GetMission(id)` fornece a missão; o BFF compõe com `CompanyQuery.GetCompany(empresa_id)`; listas usam `MissionQuery.ListMissions(cursor, limit)` |
| Empresas e Missões → consumidores internos | Fila, assíncrono | `mission.created.v1`, `mission.updated.v1`, `mission.closed.v1` |
| Integrações → parceiro cadastrado | Webhook HTTPS, assíncrono | Opcional: entregar `mission.created.v1` e `mission.closed.v1` a partir de eventos internos, com assinatura e retentativas |

A consulta gRPC não reserva nem bloqueia a missão. Definir a regra da corrida entre fechamento e candidatura antes de publicar o fluxo. Webhooks não acessam tabelas diretamente. Atualização ao vivo de listagens de missões não faz parte da primeira etapa; não abrir WebSocket para isso.

### candidaturas — vínculo entre talento e missão

| Campo | Significado |
|---|---|
| `id` | UUID da candidatura |
| `talento_id` | FK obrigatória para `talentos.id_auth` |
| `missao_id` | FK obrigatória para `missoes.id` |
| `mensagem` | Apresentação opcional enviada com a candidatura |
| `status` | Situação atual; padrão `PENDENTE` |
| `created_at` | Instante da criação; padrão `now()` |

`STATUS_CANDIDATURA` admite `PENDENTE`, `APROVADA`, `RECUSADA` e `CANCELADA`. O enum limita os valores, mas não implementa regras de transição, propriedade ou autorização. Essas regras devem ser definidas antes de disponibilizar a alteração de situação. Não há histórico de alterações nesta versão.

#### Comunicação de candidaturas

Responsável: Candidaturas. Criação e alteração de situação são comandos REST; consultas combinadas podem usar GraphQL.

| Origem → destino | Tipo | Operação e contrato planejado |
|---|---|---|
| Frontend → Candidaturas, via Gateway | REST/HTTPS, síncrono | `POST /v1/missions/{id}/applications`, `GET /v1/applications/{id}`, `PATCH /v1/applications/{id}`; na criação o cliente envia mensagem; no PATCH envia somente a situação permitida; o talento vem do ator autenticado, nunca de um ID arbitrário |
| Candidaturas → Talentos / Empresas e Missões | gRPC unary, síncrono | Validar perfil e consultar missão antes da inscrição; falha de dependência não autoriza prosseguir |
| BFF GraphQL → Candidaturas | gRPC unary, síncrono | `ApplicationQuery.GetApplication(id)`, `ListMyApplications(cursor, limit)` e `ListMissionApplications(mission_id, cursor, limit)`; aplicar acesso do ator |
| Candidaturas → Notificações / Indicadores / Integrações | Fila, assíncrono | `application.created.v1`, `application.status.changed.v1`; consumidores independentes |
| Notificações → navegador autenticado | SSE/HTTPS, unidirecional | `GET /v1/notifications/stream`; emitir `application.status.changed` somente para destinatários autorizados |
| Integrações → parceiro autorizado | Webhook HTTPS, assíncrono | Entrega opcional de mudança de situação; sem dados pessoais desnecessários |

SSE avisa que houve mudança; a consulta REST ou GraphQL recupera o estado autorizado atual. O navegador não altera a candidatura por SSE. GraphQL planejado: `minhasCandidaturas` e `candidaturasDaMissao`, com paginação.

WebSocket será usado por Comunicação para um futuro chat relacionado à missão/candidatura. A candidatura fornece contexto e autorização; mensagens pertencem a outra entidade, ainda fora do DBML.

## Relacionamentos e integridade

| Origem | Destino | Cardinalidade |
|---|---|---|
| Conta | Perfil de talento | Uma conta possui zero ou um perfil; cada perfil tem exatamente uma conta |
| Conta | Perfil de empresa | Uma conta possui zero ou um perfil; cada empresa tem exatamente uma conta |
| Empresa | Missões | Uma empresa possui zero ou várias missões; cada missão pertence a uma empresa |
| Talento | Candidaturas | Um talento possui zero ou várias candidaturas; cada candidatura pertence a um talento |
| Missão | Candidaturas | Uma missão possui zero ou várias candidaturas; cada candidatura pertence a uma missão |

Talentos e missões possuem uma relação muitos-para-muitos representada pela tabela `candidaturas`, que também guarda mensagem e situação.

Para `prazo`, o fuso de negócio e a inclusão do último dia precisam ser decididos; uma data não define sozinha um instante de encerramento. O booleano `aberta` não representa rascunho ou pausa. Não há campo de situação/desativação próprio em `talentos`; `auth.ativo` controla a conta, e uma política separada de perfil exigirá evolução do modelo.

O índice único `(talento_id, missao_id)` impede duplicidade, inclusive sob concorrência. Cancelar não remove essa restrição: uma nova inscrição no mesmo par não pode criar outra linha. Reabertura exige uma regra futura explícita.

O banco garante existência dos registros referenciados e unicidade, mas não garante compatibilidade entre papel e perfil. A aplicação deve permitir perfil profissional para `USER` e perfil empresarial para `EMPRESA`, sem conceder perfis automaticamente a `ADMIN`. O DBML, isoladamente, não impede dois tipos de perfil para a mesma conta.

Não há exclusão em cascata definida. Ao gerar as migrations, manter FKs com `NO ACTION`, impedindo exclusões que deixem dependentes órfãos. Desativar a conta por `ativo` preserva seus vínculos; não exclui perfis, missões ou candidaturas.

## Convenções de implementação

- Nomes físicos de tabelas e colunas usam `snake_case`; propriedades Java podem usar `camelCase` com mapeamento explícito.
- `uuid` corresponde a `UUID`, `date` a `LocalDate` e `timestamptz` a `OffsetDateTime` ou `Instant` com mapeamento adequado.
- `timestamptz` representa um instante; não preserva o nome do fuso nem o offset original. Usar UTC nas trocas e converter para exibição.
- Persistir enums por nome, nunca por posição. As migrations devem criar tipos PostgreSQL compatíveis com os nomes do DBML e o mapeamento ORM escolhido.
- O default de `updated_at` só atua na inserção. Atualizar esse campo na aplicação ou por mecanismo explícito no banco.
- Gerar o hash da senha com BCrypt ou mecanismo equivalente já configurado no projeto. Nunca retornar o hash em respostas.
- Verificar `ativo` no acesso protegido; definir como a desativação afeta tokens já emitidos. O campo sozinho não revoga JWTs.
- Validar textos obrigatórios não vazios e URLs na aplicação; `not null` não impede strings vazias.
- Validar propriedade: a empresa só gerencia suas missões e o talento só envia candidaturas em seu próprio nome.
- Antes de candidatar, validar conta ativa, perfil existente e missão aberta. A política de prazo e a corrida com fechamento da missão exigem definição e implementação transacional apropriada.

## Diferenças em relação ao código existente

Este é um contrato de destino, não um retrato do banco executado. No Auth Service inspecionado, a entidade é `UserAuth`, a coluna da senha se chama `password`, o bloqueio usa `revoked` e não há nome físico `auth` declarado por `@Table`. O enum existente tem `USER` e `EMPRESA`; `ADMIN` ainda precisa ser implementado.

Para adotar este contrato será necessário mapear ou migrar nomes, incluir `ADMIN`, alinhar a persistência PostgreSQL e tratar `ativo` como o inverso de `revoked` na migração. O JWT atual identifica o usuário por username; o vínculo seguro desse ator ao UUID dos perfis deve ser implementado. Nenhuma dessas alterações de aplicação ou migração está incluída neste documento.

## Escopo e evolução

As comunicações assíncronas, SSE, webhooks e chat descritas acima são etapas de evolução; exigem persistência operacional adicional antes de uso confiável. Ficam fora do modelo inicial: sessões persistidas, refresh tokens, múltiplos papéis por conta, tabelas de permissões, habilidades normalizadas, experiências individuais, membros de empresa, histórico de candidaturas, chat, notificações, métricas, integrações e Outbox. A inclusão futura exige decisão, atualização do modelo e migrations.

## Critérios de aceite da implementação futura

1. Criar banco vazio com migrations e verificar tipos, defaults, FKs e unicidade conforme o DBML.
2. Rejeitar username duplicado e referências a conta, empresa, talento ou missão inexistentes.
3. Impedir dois perfis do mesmo tipo para uma conta e duas candidaturas para o mesmo par, inclusive simultâneas.
4. Verificar hash da senha, acesso com conta inativa, autorização por proprietário e atribuição controlada de papel.
5. Verificar datas e atualização de `updated_at`.
6. Confirmar que exclusão de registros referenciados não produz órfãos.

Esses critérios orientam a implementação. Não foram executados como testes de aplicação nesta alteração documental.
