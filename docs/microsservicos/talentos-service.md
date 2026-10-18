# Talentos — responsabilidades

## Finalidade

Manter a representação profissional de uma pessoa na plataforma. Projeto: `services/talentos-service/`. Domínio: S1.

## Responsabilidades

- Criar, consultar, atualizar e desativar perfis profissionais conforme a política aprovada.
- Manter habilidades, experiências e itens de portfólio vinculados ao talento.
- Validar os dados profissionais e a propriedade do perfil nas alterações.
- Controlar quais informações podem ser consultadas por empresas e outros participantes.
- Disponibilizar os dados necessários para verificar a existência e a situação de um talento.
- Comunicar alterações relevantes de perfil por contrato de evento, quando implementado.

## Dados próprios

Na versão inicial, existe apenas `talentos`, com `id_auth` como PK/FK para `auth.id`. Habilidades e experiências são textos e o portfólio é um link. Tabelas separadas de habilidades, experiências e itens de portfólio são evolução. Senha e token não fazem parte do perfil profissional.

## Integrações propostas

| Participante | Relação |
|---|---|
| Auth Service | Relacionar a conta autenticada ao proprietário do perfil |
| Candidaturas | Fornecer dados de existência/situação necessários à elegibilidade |
| Indicadores | Fornecer eventos de alteração para métricas aprovadas |
| Frontend via Gateway | Oferecer consulta e manutenção do perfil |

## Limites

Candidaturas mantém o vínculo entre talento e missão. Empresas e Missões mantém as oportunidades. Talentos fornece informações do perfil, mas não registra candidaturas nem decide o resultado de seleção.

## Regras e decisões pendentes

Os campos e obrigatoriedades iniciais estão no modelo oficial. Definir visibilidade, política de desativação/anonimização e eventual upload de portfólio. Exclusão de perfil não deve apagar automaticamente o histórico de outro domínio; retenção exige decisão conjunta.

## Verificação esperada

O proprietário consegue manter seu perfil; outro usuário não consegue alterá-lo. Habilidades, experiências e portfólio ficam vinculados ao perfil correto. Consultas retornam apenas os campos permitidos ao solicitante.


Consulte também as [regras comuns](../arquitetura.md), o [guia local](../desenvolvimento-local.md) e as [pendências gerais](../pendencias.md).

## Contrato inicial e canais de comunicação

Para as cinco entidades do núcleo, consultar o [modelo oficial por entidade](../modelo-dados-oficial.md). REST recebe comandos; gRPC atende consultas internas remotas; GraphQL compõe leituras no BFF. Notificações usa fila e SSE, Integrações usa fila e webhooks, e Comunicação usa WebSocket para chat. Dados operacionais desses três serviços e de Indicadores não estão nas cinco tabelas e exigem evolução antes da implementação. Detalhes, etapas e falhas estão no [guia de comunicação](../comunicacao-entidades.md).
