# Glossário

Termos do domínio e seus significados. Use-os de forma consistente em APIs, eventos, tabelas e documentação.

| Termo | Definição | Observação |
|---|---|---|
| Talentos & Missões | Nome da plataforma | Contexto |
| Talento | Pessoa profissional cadastrada na plataforma, com perfil, habilidades, experiências e portfólio | S1 |
| Empresa | Organização cadastrada que publica missões | S2 |
| Missão | Oportunidade publicada por uma empresa, com requisitos e prazos | S2 |
| Candidatura | Registro de interesse de um talento em uma missão, com estado e histórico | S3 |
| Processo de seleção | Conjunto de estados e transições associados a uma candidatura | S3 |
| Notificação | Mensagem para um usuário por e-mail e/ou plataforma | S4 |
| Indicador | Métrica ou medida de uso, operação ou impacto | S5 |
| Comunicação em tempo real | Troca de mensagens entre empresa e talento, possivelmente persistida | S6 |
| Identidade | Representação de um ator autenticável (usuário, serviço) | S7 |
| Credencial | Segredo usado para autenticação (senha, chave) | S7 |
| Papel (role) | Conjunto de permissões atribuído a uma identidade | S7 |
| Permissão (permission) | Capacidade autorizada sobre um recurso (`missions:read`, etc.) | S7 |
| Access token | Token de curta duração que autoriza requisições | S7 |
| Refresh token | Token de longa duração usado para renovar access tokens | S7 |
| Sessão | Estado de autenticação ativa associado a um usuário/dispositivo | S7 |
| Webhook | Requisição HTTP de um sistema externo notificando um evento | S8 |
| Evento | Fato imutável que ocorreu no domínio; publicado por um serviço | 06-eventos |
| Outbox | Padrão para publicar eventos de forma transacional | 03-arquitetura |
| DLQ (Dead Letter Queue) | Fila de mensagens que falharam após retentativas | S4/S8 |
| Correlation ID | Identificador que vincula operações da mesma requisição/fluxo | Observabilidade |
| Idempotência | Propriedade de uma operação poder ser executada repetidamente com o mesmo efeito | Confiabilidade |
| LGPD | Lei Geral de Proteção de Dados (Brasil) | Segurança |
| MVP | Minimum Viable Product | Escopo |
| ADR | Architecture Decision Record | Governança |