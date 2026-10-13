# Visão Geral da Arquitetura

## 1. Modelo arquitetural inicial (proposta)

**Proposta (ADR-001):** iniciar como **monólito modular** com fronteiras de domínio claras, persistência separada por schema (ou banco) por domínio quando justificável, e extração incremental de processos (workers e, eventualmente, serviços) conforme necessidade.

Justificativa: o escopo atual não possui evidência de necessidade de microsserviços independentes; a complexidade operacional e de consistência deve ser minimizada no início.

## 2. Visão em camadas

```
┌──────────────────────────────────────────────────────────────┐
│ Clientes (web, mobile, admin)                                │
└───────────────┬──────────────────────────────────────────────┘
                │ HTTPS (REST/WS/SSE propostos por fluxo)
┌───────────────▼──────────────────────────────────────────────┐
│ API Gateway / BFF (proposta opcional)                        │
└───────────────┬──────────────────────────────────────────────┘
                │
┌───────────────▼──────────────────────────────────────────────┐
│ Núcleo da plataforma (monólito modular)                      │
│  S1 Talentos  S2 Empresas/Missões  S3 Candidaturas            │
│  S5 Indicadores  S6 Comunicação  S7 Identidade               │
│  S8 Integrações  (S4 Notificações como worker)                │
└───────┬───────────────────────┬───────────────────┬─────────┘
        │                       │                   │
┌───────▼──────┐        ┌───────▼──────┐   ┌───────▼──────┐
│ DB por       │        │ Broker de    │   │ Cache        │
│ domínio      │        │ eventos      │   │ (proposta)   │
└──────────────┘        └──────────────┘   └──────────────┘
        │
┌───────▼──────┐
│ Sistemas     │
│ externos     │
└──────────────┘
```

## 3. Separação lógica vs física

- Oito domínios lógicos (S1–S8) existem independentemente da implantação.
- Implantação inicial recomendada: uma aplicação principal + workers + infraestrutura compartilhada.
- A extração para processos/serviços independentes é uma evolução, não o ponto de partida. Critérios em ADR-001.

## 4. Propriedade dos dados

Cada domínio é autoridade exclusiva de suas tabelas/coleções. Outros domínios referenciam por IDs e consomem eventos. Ver `07-dados/modelo-logico.md`.

## 5. Interfaces externas da plataforma

- API HTTP pública para clientes (proposta).
- API interna via chamadas in-process no monólito (sem rede) inicial.
- Eventos assíncronos para notificações, indicadores e integrações.
- Webhooks inbound para S8. Webhooks outbound apenas se necessário (pendente).## 6. Divergência registrada (não resolver silenciosamente)

- ADR-001 inicial recomenda monólito modular.
- A arquitetura-alvo proposta no segundo prompt considera 8 microsserviços Java 25 + Gateway + frontend Next.js.
- Ambas são propostas; a decisão final deve passar por ADR-001 atualizado/mantido.
- Até aprovação, manter domínios como módulos com fronteiras rigorosas.
