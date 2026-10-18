# Indicadores — responsabilidades

## Finalidade

Transformar fatos da plataforma em métricas de negócio e consultas agregadas. Projeto: `services/indicadores-service/`. Domínio: S5.

## Responsabilidades

- Receber eventos relevantes para as métricas aprovadas.
- Identificar eventos já processados e evitar contagem duplicada.
- Agregar medidas por período e dimensões definidas pelo produto.
- Manter séries temporais e visões de consulta.
- Expor indicadores com escopo adequado para empresa ou operação da plataforma.
- Informar a atualização dos dados e permitir reconciliação de agregados conforme política definida.

## Dados próprios

Eventos de métrica (`metric_event`) e séries agregadas (`metric_series`). Fórmula, unidade, dimensões e janela de cada métrica devem ser documentadas antes da implementação. A chave do agregado precisa distinguir todas as dimensões utilizadas.

## Integrações propostas

| Participante | Relação |
|---|---|
| Talentos / Empresas e Missões | Fornecer fatos sobre perfis e oportunidades |
| Candidaturas | Fornecer criação e mudanças de estado |
| Notificações / Comunicação | Fornecer fatos de uso ou entrega quando aprovados |
| Frontend via Gateway | Consultar painéis e séries agregadas |

## Limites

S5 calcula indicadores a partir dos fatos dos produtores. Não modifica candidaturas, missões ou notificações para ajustar uma métrica. A telemetria técnica de CPU, latência HTTP e saúde das aplicações pertence à infraestrutura de observabilidade; não substitui os indicadores de negócio.

## Regras e decisões pendentes

Definir quais métricas existem, como são calculadas, quem pode consultá-las e o atraso aceitável. Tratar eventos atrasados ou fora de ordem e definir como recalcular dados sem duplicar contagens. Evitar dados pessoais desnecessários nas dimensões e nos resultados.

## Verificação esperada

Uma massa conhecida de eventos produz o agregado esperado. Reprocessar o mesmo evento não aumenta o valor novamente. Consultas respeitam o escopo da empresa e deixam claro o período e a atualização dos dados.


Consulte também as [regras comuns](../arquitetura.md), o [guia local](../desenvolvimento-local.md) e as [pendências gerais](../pendencias.md).

## Contrato inicial e canais de comunicação

Para as cinco entidades do núcleo, consultar o [modelo oficial por entidade](../modelo-dados-oficial.md). REST recebe comandos; gRPC atende consultas internas remotas; GraphQL compõe leituras no BFF. Notificações usa fila e SSE, Integrações usa fila e webhooks, e Comunicação usa WebSocket para chat. Dados operacionais desses três serviços e de Indicadores não estão nas cinco tabelas e exigem evolução antes da implementação. Detalhes, etapas e falhas estão no [guia de comunicação](../comunicacao-entidades.md).
