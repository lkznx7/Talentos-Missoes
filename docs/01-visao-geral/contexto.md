# Contexto do Projeto Talentos & Missões

## 1. Situação (status: pendências existentes)

O projeto **Talentos & Missões** é uma plataforma que conecta talentos a empresas por meio de missões ou oportunidades publicadas na plataforma. A plataforma permite que pessoas profissionais publiquem perfis, habilidades, experiências e portfólios, e que empresas publiquem missões com requisitos e prazos. Talentos podem candidatar-se às missões, acompanhar o processo de seleção e comunicar-se com empresas; empresas podem acompanhar candidaturas e notificar usuários. A plataforma também prevê indicadores, métricas de impacto, comunicação em tempo real e integrações externas por webhooks.

## 2. Problema que o sistema resolve

Hoje não há, no contexto documentado, uma plataforma unificada que:

1. Centralize perfis profissionais e missões publicadas.
2. Reduza fricção na descoberta mútua entre talentos e empresas.
3. Registre candidaturas e o histórico de seleção de forma auditável.
4. Notifique os participantes de forma confiável sobre eventos relevantes.
5. Exponha indicadores de uso e impacto.
6. Ofereça comunicação em tempo real entre empresas e talentos.
7. Permita integração com sistemas externos por webhooks.

## 3. Escopo lógico inicial (definido como ponto de partida)

A plataforma é decomposta em oito domínios lógicos:

| ID | Domínio | Responsável por |
|---|---|---|
| S1 | Talentos | Perfis, habilidades, experiências, portfólios |
| S2 | Empresas e Missões | Cadastro de empresas, criação/publicação/gestão de missões |
| S3 | Candidaturas | Registro de candidaturas, estado/histórico, prevenção de duplicidade |
| S4 | Notificações | E-mail e plataforma, tentativas, estados de entrega, DLQ |
| S5 | Indicadores e Dados | Métricas operacionais e de negócio, séries temporais |
| S6 | Comunicação em Tempo Real | Mensagens, persistência/entrega/reconexão, histórico |
| S7 | Identidade e Acesso | Identidades, credenciais, autenticação, autorização, tokens |
| S8 | Integrações | Webhooks, validação, persistência, deduplicação, processamento |

Estes são **domínios lógicos**, não necessariamente microsserviços independentes (ver ADR-001).

## 4. Limitações da documentação inicial

- Regras de negócio específicas não foram fornecidas: limites de candidaturas, etapas obrigatórias do funil de seleção, pagamentos, avaliações, prazos de retenção, requisitos regulatórios específicos.
- Métricas-alvo (SLA, RPS, latência) não foram fornecidas.
- Stack tecnológica não foi definida (banco, broker, runtime, framework web).
- As decisões arquiteturais iniciais são propostas, não aprovadas.

Essas lacunas estão registradas em [decisoes-pendentes.md](../11-governanca/decisoes-pendentes.md).

## 5. Domínios adjacentes e sistemas externos

Sistemas externos considerados (parcialmente definidos):

- Provedores de identidade ou autenticação (a confirmar).
- Provedor de e-mail (a confirmar).
- Sistemas de parceiros via webhooks (a confirmar).
- Ferramentas de observabilidade e monitoramento (a confirmar).

Seu impacto é descrito em [atores-e-sistemas-externos.md](atores-e-sistemas-externos.md).