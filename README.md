# 💰 ContaAqui — API (contaaqui-service)

> Plataforma de controle financeiro pessoal: registre entradas e saídas, organize por categorias e acompanhe o saldo da sua carteira.

Este repositório contém a **API REST** do ContaAqui. O projeto é dividido em três repositórios:

| Repositório | Papel |
|---|---|
| **[contaaqui-service](https://github.com/thiagoocms/contaaqui-service)** (este) | API REST: regras de negócio, autenticação e segurança |
| [contaaqui-core](https://github.com/thiagoocms/contaaqui-core) | Biblioteca compartilhada: entidades, DTOs, repositórios e filtros dinâmicos |
| [contaaqui-ui](https://github.com/thiagoocms/contaaqui-ui) | Front-end web em Angular |

---

## 🎯 Visão de negócio

**Problema:** muita gente não sabe para onde vai o próprio dinheiro. Planilhas dão trabalho e os apps de banco mostram só uma conta.

**Solução:** o ContaAqui centraliza a vida financeira do usuário num só lugar:

- **Carteira (Wallet):** cada usuário tem uma carteira com saldo, criada automaticamente no cadastro.
- **Transações:** registro de **entradas** (salário, vendas…) e **saídas** (contas, compras…) com valor, data e categoria.
- **Categorias:** organização dos gastos (Moradia, Alimentação, Transporte, Lazer…) para entender onde o dinheiro está indo.
- **Login seguro em duas etapas:** além de usuário e senha, o usuário recebe um **código de acesso por e-mail**.
- **Perfis de acesso:** `ADMIN`, `EMPLOYEE` e `USER`, o que prepara a plataforma para uso pessoal e para atendimento/backoffice.

**Público-alvo:** pessoas físicas que querem controlar o orçamento sem complicação. O cadastro é identificado por documento (CPF, RG ou CNH).

---

## 🛠️ Visão técnica

### Stack
- **Java 21** + **Spring Boot 3.5**
- **Spring Security** + **OAuth2 Resource Server** (JWT assinado com **RSA**)
- **Spring Data JPA / Hibernate** + **PostgreSQL**
- **Spring Mail** (envio do código de acesso)
- **i18n** de mensagens e erros (pt / en)
- **Docker** (build multi-stage)

### Arquitetura
```
                 ┌──────────────────┐
  Angular (UI) ─▶│ contaaqui-service│──▶ PostgreSQL
                 │  REST / Security │
                 └────────┬─────────┘
                          │ dependência Maven
                 ┌────────▼─────────┐
                 │  contaaqui-core  │  entidades, DTOs, repositórios,
                 └──────────────────┘  specifications (filtros)
```

Camadas do serviço:
- `rest/`: controllers REST
- `service/`: regras de negócio
- `validation/`: validações de criação, atualização e relacionamentos
- `mapper/`: conversão entre entidade e DTO
- `common/`: **CRUD genérico** (`AbstractCrudResource`, `AbstractCrudService`, `AbstractCrudValidation`)

Destaques:
- **CRUD genérico reaproveitável:** um recurso novo precisa apenas estender as classes base.
- **Filtros dinâmicos** com JPA Specifications, além de **paginação e ordenação** em todas as listagens.
- **Auditoria automática** (`AuditorAwareImpl`) e **exclusão lógica** (soft delete).
- **Tratamento global de erros** (`GlobalExceptionHandler`) com mensagens traduzidas.
- **Stateless:** nenhuma sessão é guardada no servidor, só JWT.

### 🔐 Fluxo de autenticação (2FA por e-mail)
1. `POST /auth` com as credenciais em Base64 (`login:senha`), **sem** PIN → o sistema valida a senha, gera um **código** e o envia por e-mail.
2. `POST /auth` com as credenciais **e** o PIN → o sistema valida o código e devolve um **JWT** (válido por 2h) com `userId`, `profile` e `walletId`.

### 📡 Endpoints
Base: `/conta-aqui-service/api/v1`

| Recurso | Endpoints |
|---|---|
| `/auth` | `POST`: login (público) |
| `/users` | `POST` (cadastro público), `GET`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}` |
| `/wallets` | CRUD completo |
| `/categories` | CRUD completo |
| `/transactions` | CRUD completo |

Todas as listagens aceitam `?page=0&size=20&sort=campo,asc` e filtros pelos campos do recurso.

---

## ▶️ Como rodar

**Pré-requisitos:** Java 21, Maven e PostgreSQL.

1. Instale o `contaaqui-core` localmente:
   ```bash
   git clone https://github.com/thiagoocms/contaaqui-core
   cd contaaqui-core && ./mvnw clean install
   ```
2. Configure o `application.yml` com o banco, o SMTP e as chaves RSA (`app.key` / `app.pub`).
3. Suba a API:
   ```bash
   ./mvnw spring-boot:run
   ```
   A API fica em `http://localhost:5000/conta-aqui-service/api/v1`.

**Com Docker:**
```bash
docker build -t contaaqui-service .
docker run -p 5000:5000 contaaqui-service
```

---

## 🗺️ Próximos passos
- [ ] Atualização automática do saldo da carteira a cada transação
- [ ] Endpoints de relatórios para alimentar o dashboard
- [ ] Documentação OpenAPI/Swagger
- [ ] Testes automatizados

---

Desenvolvido por **[Thiago](https://github.com/thiagoocms)**
