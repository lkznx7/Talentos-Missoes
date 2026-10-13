# ADR-004 — Backend Java 25

- **Status:** Proposta
- **Data:** 2026-10-13
- **Responsáveis:** A definir
- **Decisões relacionadas:** ADR-001, ADR-011

## 1. Contexto
Backend deverá ser Java 25, com módulos por domínio.

## 2. Forças e requisitos
Compatibilidade, ecossistema maduro, produtividade.

## 3. Alternativas consideradas
A) Java 25 com Spring Boot 4.1.x. B) Java 21 LTS com Spring Boot 3.5.x.

## 4. Decisão
Proposta: **Java 25 com Spring Boot 4.1.x** (ou última stable compatível), caso aprovado Java 25.

## 5. Justificativa
Atende versão exigida e suporte a Java 25.

## 6. Consequências
### Positivas
Recursos modernos, suporte Spring.
### Negativas
JDK feature release (não LTS) pode ter ciclo curto; compatibilidade de libs a validar.
### Riscos
Plugins e libs precisam suportar Java 25.

## 7. Impactos
Toolchain, Docker images, CI.

## 8. Critérios de validação
Build de todos os módulos e testes em CI.

## 9. Condições de revisão
Se Java 25 causar incompatibilidade ou equipe preferir Java 21 LTS.

## 10. Referências
`docs/03-arquitetura/arquitetura-backend.md`