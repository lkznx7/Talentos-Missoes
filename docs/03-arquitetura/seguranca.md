# Segurança (Visão Arquitetural)

## 1. Autenticação

- S7 responsável por identidade e credenciais.
- Access token de curta duração (JWT assinado) para autorização de requisições.
- Refresh token rotativo, armazenado de forma segura (httpOnly cookie ou armazenamento seguro), revogável.
- M2M: segredos em cofre; rotação planejada.

## 2. Autorização

- RBAC + verificação por recurso (proposta).
- Princípio do menor privilégio.
- Contexto de tenant/empresa deve ser aplicado (política pendente).

## 3. Proteção de dados

- TLS em produção; mTLS para comunicação interna se separar processos.
- Dados pessoais minimizados; logs sem PII.
- Segredos fora do código; em cofre.
- Auditoria de ações sensíveis.

## 4. Validação de entrada

- DTOs validados na borda; validação não substitui sanitização/encoding de saída.
- Proteção contra injection, XSS, CSRF conforme tipo de cliente.
- Rate limiting em autenticação e endpoints sensíveis.

## 5. Webhooks

- Validação de assinatura (HMAC, algoritmo pendente).
- Proteção anti-replay (timestamp/janela) proposta.
- Persistência antes de confirmação.

Detalhes em `08-seguranca/`.