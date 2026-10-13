# ADR-013 — Cache

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-007

## 1. Contexto
Definir uso de cache.

## 2. Forças e requisitos
Desempenho vs consistência.

## 3. Alternativas consideradas
A) Sem cache inicial. B) Redis cache-aside. C) Cache em memória.

## 4. Decisão
Proposta: **não adotar cache distribuído inicialmente**, exceto sessões/pub-sub se necessário; introduzir Redis cache-aside apenas com caso de carga comprovado.

## 5. Justificativa
Evitar complexidade prematura.

## 6. Consequências
### Positivas
Menos consistência eventual.
### Negativas
Possível latência maior.
### Riscos
Sem gargalo provado.

## 7. Impactos
Serviços S5/S4 eventualmente.

## 8. Critérios de validação
Medição de latência antes/depois.

## 9. Condições de revisão
Carga comprovada.

## 10. Referências
`docs/03-arquitetura/decisoes-arquiteturais.md`