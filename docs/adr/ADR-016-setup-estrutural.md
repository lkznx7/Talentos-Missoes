# ADR-016 — Setup estrutural inicial

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-001 a ADR-012

## Contexto

O repositório contém documentação de arquitetura e ADRs 001–015, todos com status Proposta, e uma pasta `architecture/` com referências. Não havia aplicação executável. O escopo solicitado para o setup requer frontend Next.js, oito aplicações Java independentes, Gateway, Compose local e CI.

Há conflito explícito: ADR-001 e `docs/03-arquitetura/visao-geral.md` recomendam monólito modular inicialmente e registram que a arquitetura de oito processos é alvo proposto. Esta estrutura de setup atende ao escopo solicitado sem alterar o status daquela decisão.

## Decisões propostas

1. Manter a organização em monorepositório, como estrutura proposta no ADR-002.
2. Usar Next.js App Router com TypeScript e npm; manter apenas página inicial estrutural.
3. Manter projetos Maven independentes com Java 25 e Spring Boot 4.1.1; adicionar Spring Web MVC nos serviços com superfície HTTP proposta (todos exceto comunicação, cujo escopo S6 permanece pendente), Actuator e dependências de teste.
4. Usar Spring Cloud Gateway reativo 2025.1.2 com Spring Boot 4.1.1; deixar rotas configuráveis e destinos locais demonstrativos.
5. Usar PostgreSQL 18.6 no Compose como única dependência local inicial. Não habilitar broker nem Redis enquanto suas decisões permanecerem pendentes/condicionais.
6. Usar CI GitHub Actions por projeto, executando lint, tipos e build no frontend e `mvn verify` em cada projeto Java.

## Consequências e limites

- Esta proposta não aprova nem resolve ADR-001; oito processos independentes aumentam custo operacional e podem divergir da recomendação modular atual.
- Sem broker, Redis, banco configurado em serviços, autenticação, observabilidade exportada ou contratos executáveis, os projetos são somente bases estruturais.
- As rotas do Gateway não foram validadas com upstreams.
- O lockfile npm deve ser gerado e versionado quando o registry estiver acessível; sem ele, a instalação inicial não é reproduzível por lockfile.
- Versões devem ser reavaliadas antes de adoção/aprovação e atualizadas por correção de segurança.

## Alternativas consideradas

- Aguardar aprovação formal de ADR-001 antes de criar qualquer processo: incompatível com o escopo estrutural solicitado.
- Criar oito aplicações e assinalar os ADRs existentes como aprovados: rejeitado por alterar silenciosamente decisões pendentes.
- Incluir RabbitMQ e Redis no Compose de referência: rejeitado porque as tecnologias permanecem pendentes/condicionais.

## Validação

Requer CI com JDK 25 e instalação npm; validação local depende das ferramentas instaladas no ambiente.
