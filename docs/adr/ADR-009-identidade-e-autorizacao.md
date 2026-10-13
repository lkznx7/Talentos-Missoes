# ADR-009 — Identidade e Autorização

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-005

## 1. Contexto
Definir tokens, sessões, papéis e integração com IdP.

## 2. Forças e requisitos
Segurança, SSO futuro, simplicidade.

## 3. Alternativas consideradas
A) Sessões server-side. B) JWT access + refresh rotativo. C) Delegação a IdP externo (OIDC).

## 4. Decisão
Proposta: **JWT access token curto + refresh token rotativo**, com suporte a IdP externo quando disponível; identidade pode ser delegada ao IdP em fase posterior.

## 5. Justificativa
Balanceia Stateless e revogação.

## 6. Consequências
### Positivas
Escalabilidade e revogação.
### Negativas
Complexidade de rotação e segurança.
### Riscos
Token leakage.

## 7. Impactos
Gateway, frontend, auth flows.

## 8. Critérios de validação
Testes de auth/refresh/revogação.

## 9. Condições de revisão
Se SSO/IdP externo for exigido antes.

## 10. Referências
`docs/05-api/autenticacao-e-autorizacao.md`