# Implantação

## 1. Estratégia proposta

- Ambientes: `dev`, `staging`, `prod`.
- Aplicação containerizada; workers por função como processos/containers separados.
- CI/CD com etapas: build, testes, migration, deploy, smoke test.
- Rollback: reverter imagem e, se necessário, migration (expand/contract reduz risco).
- Configuração por variáveis de ambiente; segredos em cofre.

## 2. Migrations

- Expand/contract: adicionar colunas nullable → backfill (se needed) → enforcement posterior → remover legado.
- Nunca bloquear leitura/escrita prolongada em produção (avaliar).
- Backups antes de migrations críticas.

## 3. Disponibilidade inicial

- Uma instância por componente com auto-restart; escala horizontal sob demanda (proposta).
- Banco gerenciado (proposta) com backups.

## 4. Segurança de deploy

- Principle of least privilege em credenciais de deploy.
- Infra as code (proposta) para rastreabilidade.