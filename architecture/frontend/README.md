# frontend

## Árvore
```
frontend/
├── src/app/
├── src/components/
├── src/features/
├── src/lib/api/
├── src/lib/auth/
├── src/lib/validation/
├── src/hooks/
├── src/types/
├── tests/
├── .env.example
└── package.json
```

## Responsabilidade
- UI, validação cliente, autenticação de sessão, consumo do Gateway.
- Não implementa regra de negócio de domínio.

## Dependências (propostas)
- next (16.4.x), react 19.x, typescript
- zod / react-hook-form (validação)
- tanstack query (cache cliente) opcional
- playwright/vitest/jest

## Configuração
- `NEXT_PUBLIC_API_BASE_URL`
- `API_BASE_URL` (server)
- `NEXTAUTH?` não definido (auth por token)