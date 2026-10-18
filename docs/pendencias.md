# Pendências do projeto

Este documento registra decisões e integrações ainda necessárias. Detalhes de responsabilidade ficam no documento de cada serviço.

| Área | O que definir ou concluir |
|---|---|
| Auth Service | Devolver token no login; ativar validação HTTP; definir bloqueio/revogação; alinhar IDs, porta, caminho do Gateway, persistência e CI |
| Talentos | Campos obrigatórios, visibilidade, links/upload de portfólio e desativação |
| Empresas e Missões | Estados de publicação, edição com candidaturas, reabertura, prazos e membros de múltiplas empresas |
| Candidaturas | Elegibilidade, funil, responsáveis por transições, limites e corrida com encerramento da missão |
| Notificações | Provedor, destinatários, templates, canais, tentativas, DLQ e confirmação externa |
| Indicadores | Fórmulas, unidades, dimensões, janelas, atraso aceitável e visibilidade |
| Comunicação | WebSocket definido como alvo do chat; confirmar inclusão no MVP, participantes, histórico, reconexão e múltiplas instâncias |
| Integrações | Parceiros, assinatura, proteção contra replay, deduplicação e envio de eventos externos |
| Dados | Modelo inicial definido em cinco tabelas e banco compartilhado; implementar migrations e definir retenção, anonimização, exclusão e evolução do isolamento |
| API e acesso | Contratos reais por operação, erros, paginação, autorização e roteamento |
| Eventos | RabbitMQ proposto, ainda não provisionado; consolidar schemas, versões, Outbox, publicação, deduplicação e recuperação |
| Frontend | Telas, cliente de API e lockfile reproduzível |
| Qualidade | Testes reais de banco, contratos, concorrência e fluxos ponta a ponta |
| Operação | Hospedagem, deploy/rollback, rede, segredos, backup, restauração e responsáveis |
| Capacidade | Carga esperada, metas de latência/disponibilidade e limites por operação |

Redis/cache só deve ser escolhido quando houver necessidade demonstrada. Pagamentos, avaliações e outras ampliações de produto exigem confirmação de escopo.

Uma decisão concluída deve ser registrada diretamente no documento do serviço ou nas [regras comuns](arquitetura.md), substituindo a pendência por uma descrição concreta. Não tratar proposta como capacidade disponível.
