# Requisitos Não Funcionais

Convenção `RNF-XXX`. Os valores numéricos abaixo são **hipóteses para validação** até definição formal (status Proposto).

## 1. Disponibilidade e resiliência

| ID | Requisito | Métrica proposta | Status |
|---|---|---|---|
| RNF-001 | APIs públicas devem operar com alta disponibilidade. | ≥ 99,0% (hipótese) | Proposto |
| RNF-002 | Falha de serviço de notificação não deve afetar ciclo de candidaturas. | Acoplamento via eventos/DLQ | Proposta |
| RNF-003 | Processamento de webhook deve ser assíncrono e tolerar picos. | Queue lag < 5 min (hipótese) | Proposta |

## 2. Desempenho

| ID | Requisito | Métrica proposta | Status |
|---|---|---|---|
| RNF-010 | Operações de leitura comuns devem ser rápidas. | P95 < 300 ms (hipótese) | Proposta |
| RNF-011 | Operações de escrita de candidatura devem ser confiáveis. | P95 < 500 ms (hipótese) | Proposta |
| RNF-012 | Publicação de eventos não deve bloquear a resposta ao cliente quando possível. | Outbox assíncrono | Proposta |

## 3. Escalabilidade

| ID | Requisito | Status |
|---|---|---|
| RNF-020 | Componentes sem estado devem poder escalar horizontalmente. | Proposta |
| RNF-021 | Trabalhos pesados (notificações, indicadores, integrações) devem ser processados por workers independentes. | Proposta |
| RNF-022 | Persistência deve suportar particionamento/sharding apenas se necessário (não inicial). | Proposta |

## 4. Consistência e durabilidade

| ID | Requisito | Status |
|---|---|---|
| RNF-030 | Dados de perfil, empresa, missão e candidatura devem ser duráveis. | Definido (contexto) |
| RNF-031 | Eventos de negócio devem ser persistidos de forma durável antes da confirmação (outbox). | Proposta |
| RNF-032 | Consumidores devem ser idempotentes. | Proposta |
| RNF-033 | Transações devem ser escopo único por domínio; transações distribuídas devem ser evitadas. | Proposta |

## 5. Segurança e privacidade

| ID | Requisito | Status |
|---|---|---|
| RNF-040 | Toda comunicação deve ocorrer sobre TLS em produção. | Proposta |
| RNF-041 | Segredos não devem ser armazenados em código. | Definido (prática) |
| RNF-042 | Acesso a dados pessoais deve seguir LGPD (minimização, finalidade, registro). | Proposta (avaliação jurídica pendente) |
| RNF-043 | Chat/identidade devem ser autenticadas e autorizadas. | Proposta |

## 6. Manutenibilidade e operação

| ID | Requisito | Status |
|---|---|---|
| RNF-050 | Contratos de API e eventos devem ser versionados. | Proposta |
| RNF-051 | Logs estruturados, métricas e tracing devem ser padronizados. | Proposta |
| RNF-052 | Deploy deve permitir rollback. | Proposta |
| RNF-053 | Migrations devem ser compatíveis com versões anteriores durante deploy. | Proposta |

## 7. Limites e pendências

- Não foram definidos SLA/SLO, RPS, latência máxima, retenção ou RPO/RTO específicos. Ver `11-governanca/decisoes-pendentes.md`.
- Testes de carga devem estabelecer linhas de base empíricas.