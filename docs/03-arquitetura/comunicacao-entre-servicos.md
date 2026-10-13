# Comunicação entre Serviços (Java)

## Critérios de escolha
- Use **REST síncrono** apenas para consultas necessárias e quando o resultado é requerido imediatamente (validar missão, talento).
- Use **eventos assíncronos** para efeitos colaterais (notificação, métricas, integração).
- Evite chamadas síncronas em cadeia (S3 → S1 → S2) para operações que podem ser materializadas/validadas localmente.
- Messaging garante at-least-once; consumidores idempotentes.

## Serviços produtores/consumidores
Ver `06-eventos/catalogo-de-eventos.md`.

## Falhas parciais
- Timeout/redes: retries idempotentes para leituras/escritas safely; não retry automático em criações sem chave de idempotência.
- Circuit breaker se adotado.