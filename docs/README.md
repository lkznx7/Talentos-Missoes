# Documentação — Talentos & Missões

A plataforma conecta profissionais a empresas por meio de missões. O fluxo principal é manter um perfil, publicar uma oportunidade, candidatar-se e acompanhar a seleção.

## Modelo oficial inicial

- [Entidades, campos e relacionamentos](modelo-dados-oficial.md).
- [Canais e contratos de comunicação](comunicacao-entidades.md).
- [Revisão e limites de implementação](revisao-documentacao.md).

Esses documentos prevalecem para o núcleo inicial de cinco tabelas. Descrições ampliadas dos serviços representam evolução quando excedem esse modelo.

## Microsserviços

Cada serviço possui um documento principal com responsabilidades, dados, integrações, limites e pendências.

| Serviço | Documento |
|---|---|
| Auth Service | [Contas e autenticação](microsservicos/auth-service.md) |
| Talentos | [Perfis profissionais](microsservicos/talentos-service.md) |
| Empresas e Missões | [Empresas e oportunidades](microsservicos/empresas-missoes-service.md) |
| Candidaturas | [Candidatura e seleção](microsservicos/candidaturas-service.md) |
| Notificações | [Avisos e entregas](microsservicos/notificacoes-service.md) |
| Indicadores | [Métricas de negócio](microsservicos/indicadores-service.md) |
| Comunicação | [Conversas e mensagens](microsservicos/comunicacao-service.md) |
| Integrações | [Eventos de sistemas externos](microsservicos/integracoes-service.md) |

## Referências comuns

- [Arquitetura e regras comuns](arquitetura.md): fronteiras dos serviços, consistência, segurança e verificações.
- [Desenvolvimento local](desenvolvimento-local.md): comandos, portas e diagnóstico.
- [Pendências](pendencias.md): decisões necessárias para implementar e integrar o produto.

## Como interpretar

Responsabilidade é o escopo esperado do serviço; não comprova implementação. O Auth Service descreve comportamentos encontrados nos fontes, com limitações explícitas. Os demais serviços estão em desenvolvimento. Só considerar um fluxo validado após testes da implementação real.

Atualize o documento do serviço quando mudar seu escopo ou contrato. A estrutura inicial está definida no modelo oficial; migrations, contratos executáveis e validação do banco ainda precisam ser implementados. Pagamentos e avaliações não estão confirmados no escopo.
