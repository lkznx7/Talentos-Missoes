# Comunicação entre Componentes

## 1. Princípios

- Toda comunicação tem natureza explícita: síncrona (chamada e resposta no mesmo fluxo) ou assíncrona (publicação/consumo).
- REST ≠ síncrona? REST normalmente é síncrono sobre HTTP, mas notificações internas podem ser REST assíncronas (webhook) — diferenciar.
- Chamadas in-process são mais simples e seguras; rede entre módulos do monólito inicial deve ser evitada.
- Assíncrono não elimina duplicidade: consumidores devem ser idempotentes.

## 2. Mapa de comunicação por fluxo

| Fluxo | Iniciador | Natureza | Mecanismo proposto | Resultado imediato? |
|---|---|---|---|---|
| Criar candidatura | Cliente | Síncrona | REST API | Sim |
| Validar missão/talento | S3 (processo) | Síncrona in-process | chamada de módulo | Sim (dentro da transação local) |
| Disparar notificação | S3/S2 | Assíncrona | evento `application.created` → S4 worker | Não |
| Processar webhook inbound | Remetente externo | Assíncrona | HTTP inbound → persistir → 2xx rápido → worker | Não (2xx de aceite) |
| Atualização em tempo real | Cliente/Sistema | Bidirecional (se S6) | WebSocket | Sim/near-real-time |
| Indicadores | Sistema | Assíncrona | eventos → worker S5 | Não |

## 3. Protocolos por necessidade

| Protocolo | Quando usar | Quando não usar |
|---|---|---|
| REST/JSON | CRUD, consultas, painés, amplo tooling | Tempo real bidirecional; alto fan-out de eventos |
| WebSocket | Chat/mensagens em tempo real (S6) se confirmado | Operações simples de request/response |
| SSE | Streaming unidirecional (status, notificações server→client) se escopo | Mensagens bidirecionais frequentes |
| gRPC/RPC | Não proposto inicialmente; apenas latência extrema intra-serviços | APIs públicas heterogêneas |
| Eventos (broker) | Efeitos colaterais, desacoplamento, notificações, integrações | Quando resultado precisa ser síncrono ao cliente |

## 4. Regras de timeout e falha

- Chamadas síncronas internas devem ter timeout configurável.
- Circuit breaker e retry apenas em integrações de rede externas (proposta).
- Resposta 2xx de aceite de webhook não significa processamento completo (ver 06-eventos).

## 5. Versão de contratos

- APIs por versão de caminho (`/v1/...`) proposta.
- Eventos por `schemaVersion` no envelope.