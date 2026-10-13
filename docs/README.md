# Talentos & Missões — Documentação Técnica

> Documentação de arquitetura e implementação da plataforma **Talentos & Missões**, que conecta talentos (pessoas profissionais) a empresas por meio de missões (oportunidades publicadas na plataforma).

## Sumário

- [Visão geral](01-visao-geral/contexto.md)
- [Objetivos e escopo](01-visao-geral/objetivos-e-escopo.md)
- [Glossário](01-visao-geral/glossario.md)
- [Atores e sistemas externos](01-visao-geral/atores-e-sistemas-externos.md)
- [Requisitos funcionais](02-requisitos/requisitos-funcionais.md)
- [Requisitos não funcionais](02-requisitos/requisitos-nao-funcionais.md)
- [Regras de negócio](02-requisitos/regras-de-negocio.md)
- [Casos de uso](02-requisitos/casos-de-uso.md)
- [Critérios de aceite](02-requisitos/criterios-de-aceite.md)
- [Arquitetura](03-arquitetura/README.md)
- [Diagramas](04-diagramas/)
- [APIs](05-api/convencoes.md)
- [Eventos](06-eventos/catalogo-de-eventos.md)
- [Dados](07-dados/modelo-conceitual.md)
- [Segurança](08-seguranca/ameacas-e-mitigacoes.md)
- [Qualidade](09-qualidade/estrategia-de-testes.md)
- [Operação](10-operacao/ambientes.md)
- [Governança](11-governanca/decisoes-pendentes.md)

## Convenções de status

Cada decisão, requisito derivado de inferência e pendência é classificada como:

- **Definida:** informada explicitamente no contexto do projeto.
- **Proposta:** recomendação técnica ainda não aprovada.
- **Pendente:** informação que precisa ser decidida.

Quando uma informação não foi definida, ela é registrada como pendência e, quando útil, accompagnada de alternativas, vantagens e desvantagens.

## Princípios

1. **Domínio antes de tecnologia:** requisitos e fronteiras de responsabilidade orientam escolhas técnicas.
2. **Separação de responsabilidades:** cada domínio lógico possui autoridade exclusiva sobre seus dados e invariantes.
3. **Clareza de comunicação:** toda interação entre componentes tem natureza e semântica documentadas.
4. **Tolerância a falhas declarada:** garantias finitas são documentadas; não se promete execução exatamente uma vez sem justificativa.
5. **Evolução segura:** mudanças de contrato, schema e dados são versionadas e compatíveis quando possível.

## Como usar esta documentação

- **Desenvolvedores:** comece por `03-arquitetura/visao-geral.md`, depois consulte APIs, contratos de eventos e DBML.
- **Arquitetos/revisores:** priorize ADRs, matriz de rastreabilidade e relatório de revisão de consistência.
- **Equipe de produto:** consulte requisitos, casos de uso e critérios de aceite.
- **Operação/SRE:** consulte ambientes, monitoramento, runbooks e cenários de falha.

## Escopo desta versão

Esta documentação não assume regras de negócio não definidas (ex.: limites de candidaturas, etapas obrigatórias de seleção, pagamentos, avaliações, prazos de retenção específicos). Itens pendentes estão em [decisoes-pendentes.md](11-governanca/decisoes-pendentes.md).## Novos artefatos

- ADRs: [docs/adr/README.md](adr/README.md)
- Arquitetura de implementação: [architecture/README.md](../architecture/README.md)
