# Comunicação — responsabilidades

## Finalidade

Permitir conversas entre participantes autorizados da plataforma. Projeto: `services/comunicacao-service/`. Domínio: S6. A inclusão e o escopo do recurso ainda dependem de confirmação.

## Responsabilidades propostas

- Criar ou localizar conversas conforme a regra de participação aprovada.
- Verificar se o solicitante pode enviar mensagens ou consultar o histórico.
- Receber e persistir mensagens associadas à conversa e ao remetente.
- Disponibilizar histórico com paginação e ordenação definidas.
- Entregar novas mensagens pelo protocolo escolhido.
- Tratar desconexão e retomada sem perder mensagens já confirmadas como persistidas.
- Registrar estados de entrega/leitura se fizerem parte do contrato aprovado.

## Dados próprios

Conversa (`conversation`) e mensagem (`message`). IDs de talento, empresa e eventual missão vinculam a conversa aos demais domínios. As regras de participação e os campos de controle precisam ser consolidados antes da implementação.

## Integrações propostas

| Participante | Relação |
|---|---|
| Auth Service | Reconhecer a conta que participa da conversa |
| Talentos / Empresas e Missões | Resolver vínculos dos participantes quando necessário |
| Candidaturas | Condicionar início de conversa à candidatura, somente se essa regra for aprovada |
| Notificações | Gerar aviso de nova mensagem conforme política |
| Frontend e Gateway | Estabelecer transporte de mensagens e consultar histórico |

## Limites

Conversas são mensagens entre participantes. Avisos automáticos de sistema pertencem a Notificações. S6 não altera candidaturas nem decide a seleção de talentos.

## Estado e decisões pendentes

A base documentada ainda não contém servidor web. O transporte-alvo do chat é WebSocket, conforme o guia de comunicação. Definir quem pode iniciar conversa, retenção, ordenação, confirmação e estratégia para múltiplas instâncias. A porta configurada sozinha não disponibiliza chat ou HTTP.

## Verificação esperada

Somente participantes autorizados acessam a conversa. Uma mensagem confirmada como persistida permanece no histórico após reconexão. Reenvios e múltiplas conexões seguem uma política explícita de identificação e deduplicação.


Consulte também as [regras comuns](../arquitetura.md), o [guia local](../desenvolvimento-local.md) e as [pendências gerais](../pendencias.md).

## Contrato inicial e canais de comunicação

Para as cinco entidades do núcleo, consultar o [modelo oficial por entidade](../modelo-dados-oficial.md). REST recebe comandos; gRPC atende consultas internas remotas; GraphQL compõe leituras no BFF. Notificações usa fila e SSE, Integrações usa fila e webhooks, e Comunicação usa WebSocket para chat. Dados operacionais desses três serviços e de Indicadores não estão nas cinco tabelas e exigem evolução antes da implementação. Detalhes, etapas e falhas estão no [guia de comunicação](../comunicacao-entidades.md).
