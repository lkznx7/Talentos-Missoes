# Notificações — responsabilidades

## Finalidade

Entregar avisos automáticos sobre acontecimentos da plataforma e acompanhar seu resultado. Projeto: `services/notificacoes-service/`. Domínio: S4.

## Responsabilidades

- Consumir eventos de negócio elegíveis para notificação.
- Identificar destinatários, canal e conteúdo conforme regras aprovadas.
- Criar notificações na plataforma e executar entregas por e-mail quando configuradas.
- Registrar cada tentativa, resultado e falha de entrega.
- Repetir falhas transitórias dentro de limites definidos e encaminhar falhas persistentes para tratamento.
- Permitir consulta e marcação de leitura pelo destinatário.
- Oferecer reprocessamento operacional com controle de acesso e auditoria.

## Dados próprios

Notificação (`notification`), tentativas (`notification_attempt`) e controle de deduplicação do consumidor. Estado de leitura e resultado de entrega por canal precisam de definição própria; ler um aviso na plataforma não comprova entrega de e-mail.

## Integrações propostas

| Participante | Relação |
|---|---|
| Candidaturas / Empresas e Missões | Produzir fatos que podem originar avisos |
| Comunicação | Produzir aviso de mensagem quando essa regra for aprovada |
| Provedor de e-mail | Entregar mensagens e informar resultados conforme contrato |
| Indicadores | Receber fatos necessários às métricas de entrega |
| Frontend via Gateway | Consultar notificações e registrar leitura |

## Limites

S4 comunica fatos já registrados pelo domínio responsável. Não cria candidaturas, altera missões ou mantém conversas de chat. Uma falha de entrega não desfaz a operação de negócio que originou o aviso.

## Regras e decisões pendentes

Definir provedor, templates, destinatários, canais, limite de tentativas, DLQ e retenção. Deduplicação local não garante um único envio externo quando o provedor aceita a mensagem e a resposta se perde; esse caso exige idempotência ou reconciliação específica.

## Verificação esperada

Reentrega de evento não duplica a notificação local. Tentativas ficam rastreáveis; o destinatário correto consegue consultar o aviso. Reprocessamento preserva auditoria e respeita a política de repetição segura.


Consulte também as [regras comuns](../arquitetura.md), o [guia local](../desenvolvimento-local.md) e as [pendências gerais](../pendencias.md).

## Contrato inicial e canais de comunicação

Para as cinco entidades do núcleo, consultar o [modelo oficial por entidade](../modelo-dados-oficial.md). REST recebe comandos; gRPC atende consultas internas remotas; GraphQL compõe leituras no BFF. Notificações usa fila e SSE, Integrações usa fila e webhooks, e Comunicação usa WebSocket para chat. Dados operacionais desses três serviços e de Indicadores não estão nas cinco tabelas e exigem evolução antes da implementação. Detalhes, etapas e falhas estão no [guia de comunicação](../comunicacao-entidades.md).
