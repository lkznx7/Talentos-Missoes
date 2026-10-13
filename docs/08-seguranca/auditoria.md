# Auditoria

Eventos de auditoria recomendados:
- Criação/alteração de usuário, papéis e permissões.
- Publicação/encerramento de missão.
- Transições de candidatura.
- Reprocessamento de notificações/DLQ.
- Ações de operador.
- Alteração de configuração sensível.

Formato mínimo:
- ator, ação, recurso, timestamp, correlationId, resultado, before/after quando aplicável.
- Armazenamento durável e acesso restrito.
- Prazo de retenção pendente.