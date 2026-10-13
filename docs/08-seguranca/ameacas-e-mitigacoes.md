# Ameaças e Mitigações

Ativos: identidades, perfis, candidaturas, missões, mensagens, segredos, chaves de integração.

| Ameaça | Impacto | Probabilidade | Mitigação |
|---|---|---|---|
| Força bruta no login | Médio | Média | Rate limiting, lockout progressivo, CAPTCHA operacional |
| Token roubado | Alto | Média | TLS, tokens curtos, refresh rotativo, revogação |
| IDOR (acesso a recurso alheio) | Alto | Média | Autorização por recurso e testes |
| SQL Injection | Alto | Baixa/Média | JPA/parametrização, validação |
| XSS no frontend | Médio | Média | Encoding automático React, CSP |
| CSRF | Médio | Média (se cookie) | SameSite, CSRF token ou Bearer header |
| Webhook spoofing/replay | Alto | Média | HMAC, timestamp, dedup |
| DLQ poisoning / flood | Médio | Baixa | Validação na borda, limites, monitoramento |
| Vazamento de segredos | Alto | Baixa | Cofre, scanning, least privilege |
| Abuso de API | Médio | Média | Rate limiting no Gateway |
| Exposição de PII em logs | Alto | Média | Logs sem PII, revisão |