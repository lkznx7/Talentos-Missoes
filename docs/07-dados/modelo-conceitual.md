# Modelo Conceitual

## 1. Entidades principais

- **User (S7):** identidade autenticável.
- **Talent (S1):** perfil profissional, ligado opcionalmente a User.
- **Company (S2):** organização, com membros (users).
- **Mission (S2):** oportunidade publicada por Company.
- **Application (S3):** candidatura Talent→Mission, com estado/histórico.
- **Notification (S4):** mensagem para User, com tentativas.
- **Metric/TimeSeries (S5):** indicadores derivados.
- **Conversation/Message (S6):** comunicação (escopo pendente).
- **ExternalEvent (S8):** evento de webhook recebido.

## 2. Relacionamentos de alto nível

- User 1—1 Talent (proposto, se todo talento tem login)
- User 1—N CompanyMember; Company 1—N CompanyMember
- Company 1—N Mission
- Mission N—M Talent via Application
- Application 1—N ApplicationHistory
- User 1—N Notification
- User?—? Conversation (pendente regras)
- Conversation 1—N Message

## 3. Propriedade conceitual vs física

- Relacionamentos cross-domínio são **conceituais** (por ID), não necessariamente FK físicas se bancos separados.
- Em monólito inicial com schemas separados, pode-se manter FK dentro do mesmo schema/domínio; cross-domínio via ID sem FK física (proposta).