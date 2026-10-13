# Autorização

## Modelo proposto
- RBAC + autorização por recurso.
- Papéis iniciais: `talent`, `company_admin`, `company_member`, `platform_operator`, `service`.
- Permissões: `profile:read/self`, `profile:write/self`, `mission:read`, `mission:write`, `mission:publish`, `application:read`, `application:create`, `application:transition`, `notification:read`, `metrics:read`, `chat:read/write`.
- Empresa acessa apenas seus recursos e candidaturas de suas missões.
- Talento acessa seus perfis/candidaturas e missões publicadas.
- Operador auditado.

## Onde aplicar
- Gateway valida token e rota pública/privada.
- Serviços fazem autorização por recurso; não depender apenas de papel.
- Comunicação interna usa identidade de serviço (mTLS/JWT curto).