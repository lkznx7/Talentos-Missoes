# ADR-007 — Persistência e Propriedade dos Dados

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-001

## 1. Contexto
Escolher banco compartilhado por schema ou DB por serviço.

## 2. Forças e requisitos
Consistência, custo operacional, autonomia.

## 3. Alternativas consideradas
A) Banco único com schema por domínio. B) Banco independente por serviço. C) Poliglota.

## 4. Decisão
Proposta: **banco PostgreSQL compartilhado com schemas por domínio inicialmente**, com ownership lógico forte; evoluir para DB por serviço conforme critérios.

## 5. Justificativa
Menor custo inicial e transações locais por schema.

## 6. Consequências
### Positivas
Migrations simples, operação mais simples.
### Negativas
Acoplamento operacional e escala compartilhada.
### Riscos
Acesso direto indevido entre domínios.

## 7. Impactos
Migrations, backups, segurança de acesso.

## 8. Critérios de validação
Separação de usuários/privilegios por schema, sem cross-schema reads indevidos.

## 9. Condições de revisão
Autonomia/SLA divergente por serviço.

## 10. Referências
`docs/03-arquitetura/persistencia-por-dominio.md`