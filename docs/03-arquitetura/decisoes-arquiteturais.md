# Decisões Arquiteturais (ADRs)

Formato: Contexto/Problema, Requisitos, Alternativas, Decisão, Justificativa, Vantagens/Desvantagens, Riscos, Estado.

Nenhuma decisão é marcada como aprovada sem evidência; todas são **Proposta** ou **Pendente**.

## ADR-001 — Estilo arquitetural inicial
- **Contexto:** necessidade de entregar MVP e evoluir sem carga operacional prematura.
- **Requisitos:** RNF-050/052, consistência e operação simples.
- **Alternativas:** (a) microsserviços independentes por domínio; (b) monólito modular; (c) serverless por função.
- **Decisão proposta:** monólito modular com workers separados por função.
- **Justificativa:** fronteiras lógicas fortes sem custo distribuído; transações locais; implantação simples; observabilidade mais simples.
- **Vantagens:** consistência local, baixo custo operacional, refatoração segura.
- **Desvantagens:** escala vertical inicial, deploy unitário, necessidade de disciplina de módulos.
- **Riscos:** acoplamento crescente se fronteiras não respeitadas.
- **Critérios de extração:** gargalo de escala, necessidade de deploy independente, autonomia de equipe, SLA distinto, maturidade operacional.
- **Estado:** Proposta.

## ADR-002 — Protocolo de APIs públicas
- **Contexto:** clientes web/mobile/admin consomem plataforma.
- **Requisitos:** RNF-010/011, segurança.
- **Alternativas:** REST, GraphQL, RPC.
- **Decisão proposta:** REST + JSON para CRUD e consultas; WebSocket (ou SSE) apenas para comunicação em tempo real se S6 for confirmado.
- **Justificativa:** previsibilidade, cache HTTP, tooling, curva de aprendizado; GraphQL/RPC não justificados pelo escopo atual.
- **Estado:** Proposta.

## ADR-003 — Mensageria e processamento assíncrono
- **Contexto:** notificações, indicadores e integrações dependem de efeitos colaterais confiáveis.
- **Alternativas:** chamadas diretas, fila in-memory, broker externo (RabbitMQ/Kafka/SQS), outbox.
- **Decisão proposta:** broker externo com Transactional Outbox para eventos críticos; workers idempotentes; DLQ.
- **Justificativa:** persistência durável antes da confirmação; recuperação; desacoplamento.
- **Estado:** Proposta (tecnologia pendente).

## ADR-004 — Propriedade e acesso a dados
- **Contexto:** evitar acoplamento e violação de invariantes.
- **Decisão proposta:** nenhum serviço altera tabelas de outro domínio; leituras cross-domínio via API/evento; referências por ID.
- **Estado:** Proposta.

## ADR-005 — Persistência
- **Contexto:** necessidade de ACID, consultas e migrações.
- **Alternativas:** SQL relacional, NoSQL, poliglota.
- **Decisão proposta:** banco relacional como principal para domínios transacionais; armazenamento de arquivos/objetos separado (a confirmar).
- **Justificativa:** integridade referencial intra-domínio, transações locais, maturidade.
- **Estado:** Proposta.

## ADR-006 — Autenticação
- **Contexto:** identidade e acesso seguros.
- **Alternativas:** sessões server-side, JWT access+refresh, OIDC delegado.
- **Decisão proposta:** access token JWT de curta duração + refresh token rotativo; suporte a IdP externo via OIDC quando disponível.
- **Justificativa:** stateless para validação, refresh revogável, compatibilidade.
- **Estado:** Proposta.

## ADR-007 — Cache
- **Contexto:** desempenho de leituras.
- **Decisão proposta:** cache opcional e conservador; cache-aside por leitura, TTL curto, invalidação por evento; não cachear autorização/PII sem política.
- **Estado:** Proposta.

## ADR-008 — Observabilidade
- **Contexto:** rastreabilidade em fluxos síncronos/assíncronos.
- **Decisão proposta:** logs estruturados + métricas (RED/USE) + tracing distribuído (OpenTelemetry proposto), padronizados.
- **Estado:** Proposta.

## ADR-009 — Estratégia de implantação
- **Contexto:** operação e rollback.
- **Decisão proposta:** containers + CI/CD, deploys com migrations expand/contract, rollback documentado.
- **Estado:** Proposta.

## ADR-010 — Comunicação em tempo real (S6)
- **Contexto:** requisito potencial.
- **Decisão proposta:** adiar decisão de protocolo definitivo; se confirmado, WebSocket com autenticação por token e reconexão; mensagens persistidas; SSE como alternativa mais simples para unidirecional.
- **Estado:** Pendente (escopo não confirmado).