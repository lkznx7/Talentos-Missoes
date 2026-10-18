# Arquitetura e regras comuns

## Organização do projeto

O repositório contém frontend Next.js, Gateway reativo e aplicações Spring Boot independentes. O Compose operacional fornece PostgreSQL local. Broker, cache, workers e observabilidade central ainda dependem de escolha/configuração e implementação.

| Componente | Responsabilidade |
|---|---|
| Frontend | Interface, apresentação de dados e envio de comandos |
| Gateway | Encaminhar requisições aos serviços e aplicar controles de entrada implementados |
| Microsserviços | Aplicar regras de negócio, verificar acesso e manter seus próprios dados |
| Infraestrutura | Executar dependências, armazenamento, entrega e monitoramento configurados |

A estrutura atual é de projetos independentes. A implantação em produção ainda precisa de definição. As responsabilidades de cada aplicação estão no [índice](README.md).

## Modelo e comunicação oficiais

O [modelo inicial](modelo-dados-oficial.md) define cinco tabelas em um banco PostgreSQL compartilhado, com FKs físicas. A separação em bancos é evolução. O [guia de comunicação](comunicacao-entidades.md) define REST, gRPC, GraphQL, fila, SSE, webhook e WebSocket como contratos-alvo, sem afirmar implementação. Recursos além das cinco tabelas exigem evolução do modelo.

## Fronteiras de dados e acesso

- Cada serviço escreve apenas em suas próprias tabelas; consultas entre domínios usam contratos explícitos.
- Referências entre serviços usam identificadores, sem pressupor chaves estrangeiras entre bancos.
- O serviço de negócio verifica propriedade, vínculo empresarial e acesso ao recurso. Autenticar uma conta não concede acesso a todos os dados.
- O Auth Service usa `username` no subject do JWT; a integração com os IDs de usuário dos demais serviços ainda deve ser definida.
- Respostas, eventos e logs devem limitar dados pessoais e omitir senhas, tokens e segredos.
- Alterações de seleção e ações operacionais devem registrar ator, instante e resultado; retenção e exclusão precisam de política definida.

## Comunicação e consistência propostas

Usar HTTP para operações que precisam de resposta imediata. Efeitos posteriores, como avisos e agregações, podem usar eventos após o commit da operação principal. O contrato precisa declarar entrada, resultado, erros, timeout e comportamento de repetição.

Uma consulta de missão não bloqueia seu encerramento. A corrida entre validação e gravação da candidatura precisa de regra acordada. A unicidade talento–missão deve ser garantida no banco; consultar antes de inserir não basta sob concorrência.

Quando eventos forem implementados, gravar a Outbox junto da alteração na mesma transação local. Publicar com confirmação e permitir recuperação dos pendentes. Consumidores registram deduplicação e efeito local na mesma transação. Repetições e reordenação podem ocorrer; um envio externo com resposta perdida exige reconciliação ou idempotência do provedor.

Repetir somente falhas transitórias com segurança comprovada e tentativas limitadas. Falhas persistentes precisam de diagnóstico e reprocessamento auditado. `202` de webhook significa aceite persistido, não processamento concluído.

## Gateway existente

Fonte: [application.yml](../api-gateway/src/main/resources/application.yml). Porta padrão 8080, timeout de conexão 3000 ms e de resposta 5s. O encaminhamento atual preserva `/v1`.

| Prefixo | Serviço de destino | Porta padrão do destino na rota |
|---|---|---|
| `/v1/missions/*/applications/**` | Candidaturas, antes da rota genérica de missões | 8083 |
| `/v1/talents/**` | Talentos | 8081 |
| `/v1/companies/**`, `/v1/missions/**` | Empresas e Missões | 8082 |
| `/v1/applications/**` | Candidaturas | 8083 |
| `/v1/notifications/**` | Notificações | 8084 |
| `/v1/metrics/**` | Indicadores | 8085 |
| `/v1/realtime/**` | Comunicação, ainda sem servidor web na base documentada | 8086 |
| `/v1/webhooks/**` | Integrações | 8088 |

O Auth Service atual ainda exige alinhamento: a rota legada usa `/v1/auth/**` e porta 8087; o controller usa `/auth/**` e declara porta 8081. Consultas de candidaturas sob prefixos de talento/empresa também exigem revisão de precedência. Configurar uma rota não cria o endpoint no destino.

## Qualidade e operação

Para cada funcionalidade, testar regras, acesso cruzado entre usuários/empresas, persistência, erros e integração pelo Gateway. Candidaturas exige teste de concorrência; eventos exigem reentrega e recuperação após falha. Teste de contexto e build não comprovam o fluxo completo.

Migrations devem ser versionadas e testadas em banco vazio e em atualização. Mudanças de schema precisam preservar compatibilidade durante o deploy; reverter a aplicação não desfaz dados ou eventos. Definir backup e testar restauração antes de operar dados importantes.

Monitorar erros/latência HTTP e, quando houver filas, idade dos pendentes e falhas de entrega. Metas de carga, disponibilidade, alertas e procedimentos de produção estão em [pendências](pendencias.md). Para executar a base, siga o [guia local](desenvolvimento-local.md).
