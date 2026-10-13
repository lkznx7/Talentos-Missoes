# Arquitetura Frontend — Next.js

## 1. Proposta (pendente de aprovação)
- Next.js com TypeScript, App Router.
- Frontend consome **API Gateway** (não microsserviços diretamente) sobre HTTPS.
- Server Components para leituras públicas-cacheáveis; Client Components para interação/estado.
- Route Handlers usados apenas quando houver justificativa (BFF opcional não adotado por padrão).

## 2. Árvore proposta
```text
frontend/
├── app/
│   ├── (public)/
│   ├── (auth)/
│   ├── (dashboard)/talentos/
│   ├── (dashboard)/empresas/
│   ├── (dashboard)/missoes/
│   ├── (dashboard)/candidaturas/
│   ├── (dashboard)/notificacoes/
│   └── (comunicacao)/
├── components/
│   ├── ui/
│   └── shared/
├── features/
│   ├── talentos/
│   ├── empresas/
│   ├── missoes/
│   ├── candidaturas/
│   ├── notificacoes/
│   └── comunicacao/
├── lib/api/
├── lib/auth/
├── lib/validation/
├── hooks/
├── types/
├── tests/
├── .env.example
└── package.json
```

## 3. Auth no frontend
- Access token em memória (proposta); refresh token em cookie httpOnly (proposta), evitando exposição ao JS.
- Renovação silenciosa em rota interceptadora.
- Logout revoga refresh.
- Nunca armazenar refresh em localStorage.

## 4. Comunicação HTTP
- Cliente HTTP central com timeout, cancelamento (AbortController), tratamento de erro padronizado (Problem Details).
- Tipos gerados do OpenAPI (proposto) ou manuais validados em CI.
- Paginação cursor/limit.

## 5. Tempo real
- S6 opcional: WebSocket/SSE através do Gateway, se escopo confirmado.
- Reconexão com backoff; mensagens persistidas no backend.

## 6. Variáveis de ambiente
- `NEXT_PUBLIC_API_BASE_URL` (público), `API_BASE_URL` (server-only).
- Sem segredos no bundle do navegador.

## 7. Testes
- Unit: Vitest/Jest; componentes: React Testing Library; e2e: Playwright (propostos).