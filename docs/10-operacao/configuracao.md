# Configuração

- Variáveis de ambiente por serviço (ex.: `DB_URL`, `BROKER_URL`, `JWT_PRIVATE_KEY` via cofre).
- `.env.example` versionado (sem segredos).
- Config central (Spring Config?) proposta apenas se houver necessidade; evitar complexidade prematura.
- Feature flags para funcionalidades pendentes (S6, pagamentos).