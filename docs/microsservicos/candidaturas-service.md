# Candidaturas — responsabilidades

## Finalidade

Registrar o interesse de um talento em uma missão e acompanhar seu processo de seleção. Projeto: `services/candidaturas-service/`. Domínio: S3.

## Responsabilidades

- Criar o vínculo entre talento e missão após validar as condições de elegibilidade.
- Impedir candidaturas duplicadas do mesmo talento para a mesma missão.
- Manter o estado atual da candidatura e seu histórico de alterações.
- Aplicar apenas transições permitidas e registrar ator, instante e motivo quando exigido.
- Oferecer consultas de candidatura por talento, missão e empresa com controle de acesso.
- Tratar concorrência e repetição de comandos conforme o contrato aprovado.
- Publicar fatos de criação e mudança de estado para os consumidores interessados.

## Dados próprios

Na versão inicial, somente `candidaturas`: UUID, talento, missão, mensagem, situação e criação. Histórico, versão para concorrência e snapshots são evolução. IDs de talento e missão são FKs no banco compartilhado; bancos separados exigem referências lógicas.

## Integrações propostas

| Participante | Relação |
|---|---|
| Talentos | Consultar existência e dados necessários à elegibilidade |
| Empresas e Missões | Consultar missão, empresa proprietária, estado e requisitos |
| Notificações | Receber eventos de criação/transição para gerar avisos |
| Indicadores | Receber eventos para métricas de candidatura e seleção |
| Comunicação | Eventual abertura/vinculação de conversa, se aprovada pelo produto |

## Limites

S3 decide e registra a candidatura. Não altera perfil profissional, publicação da missão ou dados de empresa. O sucesso da candidatura não deve depender da entrega imediata de e-mail.

## Regras centrais

A unicidade talento–missão precisa ser garantida no banco, inclusive sob concorrência. A primeira versão grava somente o estado atual. Ao implementar histórico, gravá-lo com o estado na mesma transação; ao implementar eventos, incluir a Outbox na transação local. Regras de elegibilidade, funil e encerramento concorrente de missão continuam pendentes de aprovação.

## Verificação esperada

Duas tentativas simultâneas geram uma única candidatura. Uma transição inválida não altera estado nem histórico. Talentos e empresas consultam apenas candidaturas permitidas. Falha após commit não perde o evento quando Outbox estiver implementada.


Consulte também as [regras comuns](../arquitetura.md), o [guia local](../desenvolvimento-local.md) e as [pendências gerais](../pendencias.md).

## Contrato inicial e canais de comunicação

Para as cinco entidades do núcleo, consultar o [modelo oficial por entidade](../modelo-dados-oficial.md). REST recebe comandos; gRPC atende consultas internas remotas; GraphQL compõe leituras no BFF. Notificações usa fila e SSE, Integrações usa fila e webhooks, e Comunicação usa WebSocket para chat. Dados operacionais desses três serviços e de Indicadores não estão nas cinco tabelas e exigem evolução antes da implementação. Detalhes, etapas e falhas estão no [guia de comunicação](../comunicacao-entidades.md).
