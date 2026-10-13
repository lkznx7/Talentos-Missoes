# ADR-003 — Frontend Next.js

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-005

## 1. Contexto
Frontend precisa suportar SEO, interação e consumo seguro do Gateway.

## 2. Forças e requisitos
Renderização, performance, segurança, realtime opcional.

## 3. Alternativas consideradas
A) SPA puro. B) Next.js App Router com Server/Client Components. C) Framework alternativo.

## 4. Decisão
Proposta: **Next.js 16.x com TypeScript e App Router**, Server Components quando beneficial e Client Components para interação.

## 5. Justificativa
Integra renderização híbrida e ecossistema amplo.

## 6. Consequências
### Positivas
Melhor UX e SEO; revalidação.
### Negativas
Complexidade de cache/revalidação; cuidado com segredos no bundle.
### Riscos
Versões/Node compatibility; vendor lock-in parcial.

## 7. Impactos
Build, lint, testes, tipos a partir do OpenAPI.

## 8. Critérios de validação
Build reproducível, métricas Core Web Vitals.

## 9. Condições de revisão
Se rendering client-only bastar ou outra stack for necessária.

## 10. Referências
`docs/03-arquitetura/arquitetura-frontend.md`