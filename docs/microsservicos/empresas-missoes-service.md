# Empresas e Missões — responsabilidades

## Finalidade

Manter as organizações participantes e as oportunidades que elas publicam. Projeto: `services/empresas-missoes-service/`. Domínio: S2.

## Responsabilidades

- Cadastrar e atualizar os dados das empresas.
- Vincular cada empresa a uma única conta na versão inicial; múltiplos membros são evolução.
- Criar missões e manter título, descrição, requisitos e prazo; rascunhos são evolução.
- Controlar abertura/fechamento por `aberta`; pausa e estados adicionais são evolução.
- Validar se o solicitante pode gerenciar a empresa ou a missão.
- Disponibilizar consultas de missões e informações necessárias à elegibilidade de candidaturas.
- Comunicar publicação e encerramento por eventos quando essa infraestrutura for implementada.

## Dados próprios

Na versão inicial, `empresas` e `missoes`. A empresa usa `id_auth` como PK/FK para `auth.id`; `missoes.empresa_id` referencia essa empresa. Não existe tabela de membros nesta etapa.

## Integrações propostas

| Participante | Relação |
|---|---|
| Auth Service | Reconhecer a conta associada ao membro da empresa |
| Candidaturas | Informar existência, estado, prazo e requisitos da missão |
| Notificações | Fornecer eventos relevantes para avisos definidos pelo produto |
| Indicadores | Fornecer fatos de publicação/encerramento para agregação |
| Frontend via Gateway | Permitir gestão e descoberta das oportunidades |

## Limites

O estado de publicação da missão pertence a S2; o estado de seleção de cada candidato pertence a Candidaturas. S2 não altera diretamente candidaturas, perfis profissionais ou entregas de notificações.

## Regras e decisões pendentes

Definir transições de publicação, edição após receber candidaturas, reabertura, prazos e política de múltiplas empresas por usuário. A corrida entre encerrar uma missão e criar uma candidatura exige uma política acordada com S3; uma consulta remota não garante atomicidade entre serviços.

## Verificação esperada

Um membro autorizado consegue gerenciar apenas as empresas permitidas. A missão segue as transições aprovadas e expõe estado coerente aos consumidores. Alterações concorrentes não sobrescrevem dados silenciosamente.


Consulte também as [regras comuns](../arquitetura.md), o [guia local](../desenvolvimento-local.md) e as [pendências gerais](../pendencias.md).

## Contrato inicial e canais de comunicação

Para as cinco entidades do núcleo, consultar o [modelo oficial por entidade](../modelo-dados-oficial.md). REST recebe comandos; gRPC atende consultas internas remotas; GraphQL compõe leituras no BFF. Notificações usa fila e SSE, Integrações usa fila e webhooks, e Comunicação usa WebSocket para chat. Dados operacionais desses três serviços e de Indicadores não estão nas cinco tabelas e exigem evolução antes da implementação. Detalhes, etapas e falhas estão no [guia de comunicação](../comunicacao-entidades.md).
