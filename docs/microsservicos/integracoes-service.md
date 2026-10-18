# Integrações — responsabilidades

## Finalidade

Controlar a troca de eventos entre a plataforma e sistemas externos. Projeto: `services/integracoes-service/`. Domínio: S8.

## Responsabilidades

- Receber webhooks dos parceiros habilitados.
- Validar origem, integridade e formato conforme o contrato de cada parceiro.
- Identificar reentregas e impedir processamento duplicado.
- Persistir a entrada duravelmente antes de confirmar o aceite.
- Processar eventos de forma assíncrona e registrar seu resultado.
- Traduzir dados externos para contratos internos sem transferir regras de domínio para o adaptador.
- Manter tentativas, falhas e reprocessamento auditável.
- Enviar webhooks externos quando a direção de saída for aprovada.

## Dados próprios

Evento externo (`external_event`), referência ao payload, chave de deduplicação, estado de processamento e entrega de webhook (`webhook_delivery`). Segredos e contratos de parceiros precisam de configuração própria por ambiente.

## Integrações propostas

| Participante | Relação |
|---|---|
| Sistemas parceiros | Enviar webhooks ou receber eventos de saída |
| Serviços de negócio | Receber comandos/eventos traduzidos e aplicar suas próprias regras |
| Broker e workers | Transportar e executar processamento desacoplado |
| Operação | Consultar falhas e realizar reprocessamento autorizado |

## Limites

S8 cuida da fronteira externa e do processamento da integração. O serviço de destino continua responsável por validar e alterar seus recursos. Não escrever diretamente nas tabelas de outros domínios para aplicar um evento recebido.

## Regras e decisões pendentes

Definir parceiros, método de assinatura, janela contra replay, IDs externos, limites de payload, retenção e política de tentativas. `202` confirma aceite persistente, não processamento concluído. Para o mesmo ID com payload diferente, definir tratamento de conflito. Saídas precisam de política de destinos e de resultado incerto.

## Verificação esperada

Origem inválida é rejeitada. Reentrega não repete o efeito local. Uma entrada aceita continua recuperável após queda do processo. Falhas permanentes são rastreáveis e reprocessadas com controle de acesso.


Consulte também as [regras comuns](../arquitetura.md), o [guia local](../desenvolvimento-local.md) e as [pendências gerais](../pendencias.md).

## Contrato inicial e canais de comunicação

Para as cinco entidades do núcleo, consultar o [modelo oficial por entidade](../modelo-dados-oficial.md). REST recebe comandos; gRPC atende consultas internas remotas; GraphQL compõe leituras no BFF. Notificações usa fila e SSE, Integrações usa fila e webhooks, e Comunicação usa WebSocket para chat. Dados operacionais desses três serviços e de Indicadores não estão nas cinco tabelas e exigem evolução antes da implementação. Detalhes, etapas e falhas estão no [guia de comunicação](../comunicacao-entidades.md).
