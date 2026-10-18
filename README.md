# Talentos & Missões

Plataforma para conectar profissionais a empresas por meio de missões. Inclui perfis, oportunidades, candidaturas, notificações, indicadores, comunicação, integrações e autenticação.

## Documentação

- [Índice e documentos de cada microsserviço](docs/README.md)
- [Arquitetura e regras comuns](docs/arquitetura.md)
- [Desenvolvimento local](docs/desenvolvimento-local.md)
- [Pendências do projeto](docs/pendencias.md)

## Repositório

| Diretório | Conteúdo |
|---|---|
| `frontend/` | Next.js com App Router e TypeScript |
| `api-gateway/` | Gateway reativo |
| `services/` | Projetos Java independentes |
| `infrastructure/compose/` | PostgreSQL local |
| `docs/` | Um documento por microsserviço e referências comuns |
| `.github/workflows/ci.yml` | Verificações configuradas de frontend e Java |

O Auth Service declara Java 21; as demais bases Java declaram Java 25. A existência de um projeto ou rota não comprova um fluxo de negócio concluído. Consulte o guia local para comandos, portas e limitações da integração atual.
