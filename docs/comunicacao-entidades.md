# Comunicação por entidade — contrato de destino

Status: arquitetura de comunicação solicitada em 18/10/2026, ainda a implementar e validar. Complementa o [modelo oficial por entidade](modelo-dados-oficial.md) e seu [DBML](dbml/talentos-missoes.dbml). Não altera as cinco tabelas nem afirma que os canais já existem.

## Responsabilidade dos canais

As entidades são dados. Quem recebe requisições, publica eventos e mantém conexões é o serviço responsável por elas. Uma FK no banco não é uma chamada de rede. REST é um estilo de API; GraphQL é uma linguagem e runtime de consulta; fila é um mecanismo de mensageria. Não são sete transportes equivalentes.

| Mecanismo | Onde usar no projeto | Direção e formato | Etapa |
|---|---|---|---|
| REST | Cadastro, login, criação e edição das cinco entidades | Frontend → Gateway → serviço; HTTPS com JSON, resposta imediata | 1 |
| gRPC | Consultas internas entre serviços e consultas do BFF | Serviço → serviço; unary RPC com Protobuf em HTTP/2, deadline | 2 |
| GraphQL | Consultas que combinam perfil, empresa, missão e candidaturas | Frontend → BFF; `POST /graphql` em HTTPS, queries autorizadas | 3 |
| Fila | Eventos após alterações, avisos, métricas e integrações | Serviço → broker → consumidores; mensagens JSON versionadas com roteamento explícito | 4 |
| SSE | Avisos de mudança de candidatura no navegador | Notificações → navegador; HTTPS `text/event-stream` | 5 |
| Webhook | Avisar sistemas externos e receber eventos de parceiros | Integrações ↔ parceiro; chamadas HTTPS independentes por evento | 6 |
| WebSocket | Chat com envio e recebimento de mensagens | Navegador ↔ Comunicação; WSS, mensagens JSON | 7 |

A ordem é uma proposta para manter a implementação gradual. Se os domínios começarem no mesmo processo, usar chamadas locais entre módulos; introduzir gRPC quando houver comunicação remota. Adotar os sete mecanismos aumenta configuração, testes e operação; nenhum cadastro precisa depender de todos eles.

## REST e GraphQL

Os caminhos REST definidos por entidade são contratos-alvo. O Auth atual usa `/auth/**`; a publicação sob `/v1/auth/**` depende do alinhamento de rotas no Gateway.

REST concentra os comandos de escrita nesta versão. GraphQL concentra consultas combinadas, sem mutations ou subscriptions na etapa inicial. O BFF é uma camada de leitura, não escreve diretamente em tabelas de outros serviços. Deve encaminhar identidade e autorização aos serviços, limitar profundidade/custo e paginar listas. Consultas em lote evitam uma chamada por linha.

Exemplo de operação planejada, dependente do futuro schema GraphQL:

```graphql
query MinhasInscricoes {
  minhasCandidaturas(limit: 20) {
    items {
      id
      status
      missao {
        id
        titulo
        empresa { nome }
      }
    }
    nextCursor
  }
}
```

REST: `201` na criação de recurso, `200` em consulta/alteração com resposta, `400` em entrada inválida, `401` sem autenticação, `403` sem autorização, `404` em recurso não disponível ao ator e `409` em duplicidade ou conflito de estado. Dependências indisponíveis produzem `503`; timeout no gateway/BFF pode produzir `504`. Não converter indisponibilidade em recurso inexistente.

GraphQL usa erros de execução no campo `errors`, inclusive com resposta HTTP `200`; erros anteriores à execução seguem o contrato HTTP da implementação. Os clientes devem tratar ambos e não assumir que todo `200` significa sucesso integral. Não expor campos privados só porque foram solicitados na query.

## gRPC

Definir contratos `.proto` versionados antes de implementar os métodos listados por entidade. Usar consultas unary, autenticação entre serviços, propagação controlada do ator, deadline e correlação. Leituras em lote também precisam de limite de tamanho.

Mapear erros de domínio para `NOT_FOUND`, `PERMISSION_DENIED`, `INVALID_ARGUMENT`, `ALREADY_EXISTS`, `UNAVAILABLE` e `DEADLINE_EXCEEDED`, conforme o caso. Não repetir comandos de escrita automaticamente sem idempotência. O navegador não se conecta diretamente ao gRPC interno.

## Publicação no Gateway

O Gateway inspecionado tem rotas HTTP por prefixo, sem rota /graphql e sem destino WebSocket configurado com esquema ws/wss. A futura publicação GraphQL exige BFF e rota própria; chat exige configuração de upgrade e destino WebSocket. SSE exige timeout de stream próprio, heartbeat e ausência de buffering: não herdar sem revisão o timeout HTTP atual de 5 segundos. gRPC interno usa endereço/porta próprios, TLS e HTTP/2, sem pressupor passagem pelas rotas REST existentes.

## Fila e eventos

Broker proposto para a etapa assíncrona: RabbitMQ, ainda sem configuração ou dependência adicionada por esta documentação. Usar um exchange de eventos e uma fila durável por consumidor, por exemplo `notificacoes.application`, `indicadores.application` e `integracoes.application`. Uma única fila compartilhada entre os três distribuiria mensagens entre eles, em vez de entregar uma cópia para cada domínio.

Envelope mínimo:

```json
{
  "event_id": "UUID",
  "event_type": "application.status.changed.v1",
  "aggregate_id": "UUID da candidatura",
  "aggregate_version": 2,
  "occurred_at": "2026-10-18T12:00:00Z",
  "correlation_id": "UUID",
  "data": {
    "talento_id": "UUID do talento",
    "missao_id": "UUID da missão",
    "empresa_id": "UUID da empresa",
    "previous_status": "PENDENTE",
    "status": "APROVADA"
  }
}
```

Os IDs do exemplo permitem resolver destinatários e escopo empresarial sem transmitir perfil completo. O evento não é um token de autorização: consumidores verificam o produtor e o acesso antes de expor dados. O schema de cada evento deve declarar esses campos e suas obrigatoriedades.

`aggregate_version` é um requisito futuro de ordenação por agregado, não um campo já presente no DBML. O consumidor descarta versões antigas quando aplicável; não pressupor ordenação global.

Para entrega recuperável, gravar evento em Outbox na mesma transação da alteração. Publicador usa mensagens persistentes, exchange/filas duráveis, publisher confirms e verificação de roteamento (por exemplo, mandatory e tratamento de retorno); confirmação do broker sozinha não garante que uma fila recebeu o evento; consumidor confirma após persistir seu efeito, com deduplicação por `event_id`. Retentativas limitadas, backoff e fila de falhas permitem diagnóstico e reprocessamento. A entrega pode se repetir; não prometer exatamente uma vez.

Outbox, deduplicação, versões e registros de entrega ainda não estão nas cinco tabelas. Antes desta etapa, ampliar o modelo e criar migrations. Não gravar a candidatura e depois publicar diretamente no broker como se as duas operações fossem atômicas. O sucesso do cadastro não depende do envio de e-mail.

## SSE

Notificações consome eventos de candidatura e publica avisos no stream autorizado de cada usuário. Em múltiplas instâncias, distribuir os avisos às instâncias que possuem conexões; uma fila com consumidores concorrentes, sozinha, não faz essa distribuição.

Autenticar a abertura e controlar expiração da conexão. Com `EventSource` nativo, usar sessão/cookie seguro e proteção de origem adequada, pois a API não permite configurar livremente um header Authorization; alternativa é streaming via fetch autenticado. Não colocar tokens duradouros na URL.

Enviar heartbeat e configurar proxy para streaming sem buffering. Reconexão não garante recuperar eventos perdidos: `Last-Event-ID` só permite replay com armazenamento e implementação explícitos. Até existir replay, consultar o estado por REST/GraphQL ao reconectar. SSE é um aviso, não a fonte oficial do estado.

## Webhook

Integrações transforma eventos internos permitidos em `POST` HTTPS ao endpoint de cada parceiro. Cada entrega tem identificador estável, assinatura HMAC e timestamp; o destinatário valida assinatura, janela temporal e deduplica. Destinos precisam ser cadastrados e validados, com controle de saída para evitar chamadas a endereços internos indevidos.

Recebimento futuro: `POST /v1/webhooks/{partner}`. Validar assinatura sobre o corpo original, registrar o evento duravelmente e só então responder `202`. O processamento acontece depois; não confirmar um aceite durável se o evento existir apenas em memória.

Retentar falhas transitórias com limite e backoff. Um timeout pode ocorrer depois de o parceiro processar a entrega, portanto repetição precisa ser segura. Entregas e eventos externos requerem novas tabelas. Não publicar credenciais, hashes, tokens ou perfis completos. Provedores e assinaturas concretas ainda precisam de contrato.

## WebSocket

Comunicação hospeda o futuro `wss://<host>/v1/realtime/chat`. Validar identidade no handshake, origem do navegador e autorização em cada conversa; limitar tamanho, frequência e filas de envio. Não confiar no identificador de remetente enviado pelo cliente.

Contratos planejados: `message.send`, `message.created`, `message.ack` e `typing.changed`. `client_message_id` permite deduplicar reenvios. Confirmação de mensagem persistida só ocorre após commit; indicadores de digitação podem ser efêmeros. Reconexão carrega histórico paginado e reenvia apenas mensagens sem confirmação.

Chat exige entidades de conversa e mensagem, ainda fora do modelo. Não reutilizar `candidaturas.mensagem`, que é apenas a apresentação enviada na inscrição. Em múltiplas instâncias, definir distribuição de mensagens e presença. WebSocket não substitui SSE de notificações nem os comandos REST nesta arquitetura.

## Fluxo de referência: candidatura

1. Navegador envia candidatura por REST; o serviço extrai o talento da identidade autenticada.
2. Candidaturas consulta Talentos e Missões por gRPC, se estiverem em processos separados.
3. Candidaturas aplica regras, grava a inscrição e a Outbox na mesma transação, quando a etapa de eventos estiver implementada.
4. Retorna o resultado REST sem aguardar avisos ou sistemas externos.
5. O publicador entrega o evento ao broker; Notificações, Indicadores e Integrações recebem cópias independentes.
6. Notificações avisa o navegador por SSE; Integrações entrega webhook a parceiros habilitados.
7. O navegador consulta o estado atualizado por REST ou GraphQL. Chat por WebSocket é um fluxo independente.

A consulta gRPC não cria transação distribuída. A política de corrida entre fechamento da missão e inscrição continua pendente; indisponibilidade de validação não pode ser tratada como aprovação.

## Validação antes de ativar cada canal

Verificar autorização por proprietário em REST, GraphQL, gRPC, SSE e WebSocket; indisponibilidade e deadline; duplicidade de candidatura; reentrega de fila e webhook; recuperação da Outbox; reconexão de SSE e chat; e ausência de credenciais nas respostas/eventos. Testar pelo Gateway os caminhos e protocolos efetivamente publicados. Nenhum desses fluxos foi implementado ou executado por esta alteração documental.

## Referências técnicas

- [HTTP — MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview)
- [gRPC: conceitos e ciclo de chamada](https://grpc.io/docs/what-is-grpc/core-concepts/)
- [GraphQL: introdução](https://graphql.org/learn/)
- [SSE — MDN](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events)
- [WebSocket — MDN](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API)
- [RabbitMQ: publish/subscribe](https://www.rabbitmq.com/tutorials/tutorial-three-java)
- [Webhooks — GitHub](https://docs.github.com/en/webhooks/about-webhooks)
