# Revisão da documentação — 18/10/2026

## Escopo e resultado

Revisados o DBML inicial, o dicionário por entidade, o guia de comunicação, o índice atual, arquitetura, pendências, guia local e documentos dos oito serviços. Comparação pontual com as rotas do Gateway e dependências de Comunicação. Não é uma validação dos fluxos executados.

A estrutura de cinco tabelas é coerente como modelo inicial compartilhado. A documentação de comunicação descreve contratos futuros; não representa APIs implementadas. O modelo oficial e o guia de comunicação prevalecem sobre propostas ampliadas.

## Correções

- Vincular o índice ao modelo oficial e retirar a afirmação de que toda a modelagem ainda está indefinida.
- Alinhar Talentos ao armazenamento inicial em texto e link, sem exigir quatro tabelas.
- Alinhar Empresas e Missões a uma conta por empresa e ao booleano `aberta`, sem pressupor membros, rascunhos ou pausa.
- Explicitar que histórico e controle de versão de Candidaturas exigem evolução, preservando a unicidade talento–missão.
- Documentar os métodos de leitura de missão, empresa e candidatura necessários ao BFF, com paginação.
- Restringir PATCH de candidatura à situação autorizada; não apresentar a mensagem inicial como editável por esse comando.
- Incluir identificadores mínimos no exemplo de evento para resolução de destinatários e escopo.
- Exigir persistência e roteamento verificável no broker, além de confirmação de publicação.
- Registrar que GraphQL, WebSocket, SSE e gRPC precisam de configuração específica: as rotas HTTP e o timeout de 5s existentes não comprovam suporte.
- Alinhar o transporte-alvo do chat a WebSocket; SSE permanece o canal de avisos.

## Decisões ainda abertas

- Transições de candidatura e quem pode executá-las; prazo, fuso e corrida com fechamento da missão.
- Contratos executáveis OpenAPI, Protobuf, GraphQL e schemas de eventos.
- Fluxo de atribuição de EMPRESA/ADMIN, UUID do ator, revogação e alinhamento do Auth atual com o modelo.
- Outbox, histórico, versões, deduplicação, notificações, entregas externas e chat exigem novas estruturas e migrations.
- Banco compartilhado facilita o início, mas acopla integridade e migrations. Bancos separados exigirão outra estratégia de consistência.

## Branches por entidade

As quatro branches novas partem do mesmo commit de revisão da `main`:

| Branch | Escopo |
|---|---|
| `feature/talentos` | Perfil profissional |
| `feature/empresas` | Perfil empresarial |
| `feature/missoes` | Oportunidades; mesmo serviço físico de Empresas |
| `feature/candidaturas` | Inscrições e seleção |

`origin/feature/auth` já existe e foi preservada; não criar outra branch de autenticação. Uma branch por entidade não cria microsserviço ou banco por entidade. Alterações locais não commitadas continuam na pasta de trabalho e não são isoladas nas branches.

## Verificação

Revisão estática de referências, entidades, canais e coerência documental; verificação do diff e dos destinos locais de links. Não foram executados testes de aplicação, banco ou comunicação. A documentação vigente consolida o modelo inicial, os contratos de comunicação e os documentos dos serviços. Código, alterações de entidades e arquivos operacionais em desenvolvimento não fazem parte desta revisão documental.

## Limpeza autorizada

As antigas pastas numeradas de documentação e ADRs descreviam uma proposta ampliada substituída pela documentação atual. Seus documentos, diagramas e exemplos de contrato já ausentes na pasta de trabalho foram removidos do conjunto ativo versionado, assim como os READMEs antigos de `architecture/`. Continuam recuperáveis pelo histórico Git. O DBML oficial, seu layout atual, o guia local, as regras de segurança e operação consolidadas, os contratos de comunicação e os documentos dos oito serviços foram preservados.

Arquivos operacionais de `architecture/` não foram incluídos nesta limpeza documental. Endpoints, regras e contratos adicionais descritos apenas nas propostas antigas não devem ser tratados como implementados; novas especificações devem seguir o modelo oficial.
