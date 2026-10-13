# Regras de Negócio

Convenção `RN-XXX`. Cada regra está classificada como Definida, Proposta ou Pendente.

## 1. Regras definidas pelo contexto

| ID | Regra | Status | Domínio |
|---|---|---|---|
| RN-001 | Um talento pode se candidatar a uma missão publicada; a candidatura pertence ao domínio S3. | Definida (contexto) | S3 |
| RN-002 | Não pode haver candidatura duplicada do mesmo talento à mesma missão. | Definida (contexto) | S3 |
| RN-003 | Notificações podem ocorrer por e-mail e pela plataforma. | Definida (contexto) | S4 |
| RN-004 | Notificações devem registrar tentativas, estados de entrega e falhas; falhas devem ir para DLQ/reprocessamento. | Definida (contexto) | S4 |
| RN-005 | Integrações externas devem validar, persistir, deduplicar e processar eventos. | Definida (contexto) | S8 |
| RN-006 | S1 tem autoridade exclusiva sobre dados de perfil; S2 sobre empresas/missões; S3 sobre candidaturas; S4 sobre notificações; S5 sobre métricas; S6 sobre mensagens; S7 sobre identidade; S8 sobre eventos externos. | Definida (arquitetura) | Todos |

## 2. Regras propostas (a aprovar)

| ID | Regra | Status | Observação |
|---|---|---|---|
| RN-010 | Uma candidatura exige talento e missão existentes e elegíveis; se missão não publicada, rejeitar. | Proposta | Elegibilidade |
| RN-011 | Estados de candidatura não podem retroceder arbitrariamente; transições definidas por state machine. | Proposta | RN-012 |
| RN-012 | Após encerrar missão, não aceitar novas candidaturas (proposta). | Proposta | pode haver reabertura |
| RN-013 | Notificações são disparadas por eventos de domínio, não por chamadas diretas de outros serviços. | Proposta | desacoplamento |
| RN-014 | Webhooks externos devem ser persistidos duravelmente antes da resposta HTTP 2xx. | Proposta | confiabilidade |
| RN-015 | Deduplicação de eventos usa identificador único do evento (`eventId`) e/ou chave de negócio. | Proposta | ver 06-eventos |
| RN-016 | Autorização é verificada por recurso + escopo. | Proposta | segurança |
| RN-017 | Dados pessoais são minimizados nas respostas e logs. | Proposta | LGPD |
| RN-018 | Exclusão de perfil pode ser lógica (soft delete) para preservar auditoria. | Proposta | retenção pendente |

## 3. Regras pendentes (decisões necessárias)

- Etapas obrigatórias do funil de seleção (ex.: triagem, entrevista, aprovação) — não definidas.
- Limites de candidaturas por talento ou missão.
- Políticas de reabertura/edição de missão com candidaturas.
- Papéis exatos e permissões.
- Política de retenção de notificações, mensagens e eventos.
- Conteúdo e formato de portfólios (arquivos, links, tamanho).
- Limites de comunicação (quem pode iniciar chat e quando).
- Pagamento/contrato formal das missões (se existir).

Essas decisões estão registradas em `11-governanca/decisoes-pendentes.md`.