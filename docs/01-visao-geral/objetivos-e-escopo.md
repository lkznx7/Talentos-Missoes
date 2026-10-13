# Objetivos e Escopo

## 1. Objetivos do projeto

| ID | Objetivo | Prioridade | Status |
|---|---|---|---|
| OBJ-001 | Permitir que talentos mantenham perfis profissionais persistentes | Alta | Definido (contexto) |
| OBJ-002 | Permitir que empresas publiquem e gerenciem missões | Alta | Definido (contexto) |
| OBJ-003 | Permitir candidatura de talentos a missões elegíveis | Alta | Definido (contexto) |
| OBJ-004 | Acompanhar o processo de seleção com histórico auditável | Alta | Definido (contexto) |
| OBJ-005 | Notificar usuários de eventos relevantes por e-mail e plataforma | Alta | Definido (contexto) |
| OBJ-006 | Expor indicadores de uso e impacto | Média | Definido (contexto) |
| OBJ-007 | Oferecer comunicação em tempo real entre empresas e talentos | Média | Definido (contexto) |
| OBJ-008 | Integrar com sistemas externos via webhooks | Média | Definido (contexto) |
| OBJ-009 | Garantir identidade, autenticação e autorização seguras | Alta | Definido (contexto) |

## 2. Escopo funcional inicial (candidato, pendente de confirmação)

São candidatas a funcionalidades, derivadas do contexto, ainda pendentes de confirmação como requisitos:

- Cadastro, atualização, consulta e desativação de perfil de talento (inclui habilidades, experiências e portfólios).
- Cadastro e manutenção de empresas, incluindo dados organizacionais.
- Criação, publicação, edição, pausa e encerramento de missões.
- Definição de requisitos e prazos das missões.
- Candidatura de talento a missão e prevenção de candidaturas duplicadas.
- Acompanhamento do estado e histórico do processo de candidatura.
- Notificações por e-mail e por plataforma (inbox/alertas).
- Registro de tentativas, estados de entrega, falhas e DLQ.
- Indicadores e métricas operacionais e de negócio.
- Séries temporais e métricas de impacto (requisitos a confirmar).
- Comunicação em tempo real: mensagens, estados de comunicação, persistência, entrega, reconexão e histórico (requisitos a confirmar).
- Identidade, credenciais, autenticação, autorização, papéis, permissões, tokens e políticas de acesso.
- Integrações externas: recebimento e envio de webhooks, validação, persistência, deduplicação e processamento de eventos.

## 3. Fora de escopo (não presumir)

Os itens abaixo **não** são assumidos como parte do produto sem confirmação:

- Pagamentos ou cobranças entre talentos e empresas.
- Avaliações formais de candidatos/empresas.
- Fluxos de onboarding obrigatório em etapas.
- Prontuários de saúde, dados sensíveis especiais.
- Regras regulatórias específicas (além da aplicação geral da LGPD quando houver dados pessoais).
- Limites quantitativos de candidaturas, missões ou usuários.
- Retenção e arquivamento por períodos definidos.

## 4. Premissas de produto

| ID | Premissa | Status |
|---|---|---|
| PM-001 | Perfis de talento e empresas são entidades distintas com identidades próprias. | Proposta |
| PM-002 | Um talento pode se candidatar a mais de uma missão; uma missão pode receber mais de uma candidatura. | Proposta |
| PM-003 | A candidatura é um vínculo entre talento e missão, com estado e histórico. | Definido (contexto) |
| PM-004 | Notificações são efeitos colaterais de eventos de negócio, não a operação primária. | Proposta |
| PM-005 | Comunicação em tempo real (S6) pode ser opcional ou ter escopo restrito. | Pendente |
| PM-006 | Integrações externas (S8) podem não estar no MVP. | Pendente |

## 5. Critérios de aceite de alto nível

O sistema será considerado aceito (MVP técnico) quando:

1. Perfis, empresas e missões forem persistidos e recuperáveis via API autenticada.
2. Candidaturas forem registradas com prevenção de duplicidade.
3. Notificações forem enfileiradas e processadas com registro de estado.
4. Identidade e autorização forem aplicadas em todas as operações.
5. Indicadores básicos forem deriváveis dos dados persistidos (pendente definição de métricas).
6. Comunicação em tempo real (se incluída) permitir envio e recebimento autenticado.

Critérios detalhados estão em `02-requisitos/criterios-de-aceite.md`.