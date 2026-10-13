# Deploy e Rollback

- Pipeline: build → test → package → image → deploy.
- Deploy progressivo/rolling com health check.
- Rollback rápido via imagem anterior.
- Migrations expand/contract para evitar incompatibilidade.
- Se migration for destrutiva, backup obrigatório.
- Pausar rollback se migration não reversível; documentar.