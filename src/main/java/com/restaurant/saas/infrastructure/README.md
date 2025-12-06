# Sistema SaaS de Gestão de Restaurantes - Spring Boot

## 📋 Contexto do Projeto

Este é um projeto de estudo focado em aprofundar conhecimentos em **Java/Spring Boot**, seguindo os mesmos padrões e práticas que foram aplicados em um projeto anterior desenvolvido em Laravel (sistema de gestão de clínica médica).

O objetivo é criar um **sistema SaaS multi-tenant completo** de gestão de restaurantes/bares, implementando todas as melhores práticas de arquitetura backend moderna.

---

## 🎯 Objetivos de Aprendizado

### Conceitos Principais a Dominar:

1. **Arquitetura Multi-tenant** (Schema per Tenant)
2. **Spring Security** com JWT
3. **Sistema de Roles e Policies** (RBAC - Role-Based Access Control)
4. **Clean Architecture** / Arquitetura Hexagonal
5. **Relacionamentos Complexos** JPA/Hibernate
6. **Migrations** com Flyway
7. **DTOs e Validações**
8. **Exception Handling Centralizado**
9. **Testes Unitários e de Integração**
10. **Documentação** com Swagger/OpenAPI

---

## 🛠️ Stack Tecnológica

### Backend
- **Java 21** (LTS)
- **Spring Boot 3.2.x**
- **Spring Data JPA** + Hibernate
- **Spring Security** + JWT
- **PostgreSQL** (database principal)
- **Flyway** (migrations)
- **Maven** (gerenciamento de dependências)
- **Lombok** (redução de boilerplate)

### Ambiente de Desenvolvimento
- **Ubuntu 24.04 LTS** (ambiente Linux nativo)
- **IntelliJ IDEA** (IDE principal)
- **PostgreSQL 16.11**
- **Postman/Insomnia** (testes de API)

---

## 🏗️ Arquitetura Multi-tenant

### Estratégia: Schema per Tenant

Cada restaurante (tenant) terá seu próprio schema no PostgreSQL:

- `public` schema: dados compartilhados (tenants, configurações globais)
- `tenant_001` schema: dados do restaurante 1
- `tenant_002` schema: dados do restaurante 2

### Identificação do Tenant

- Header HTTP: `X-Tenant-ID` ou subdomain
- Interceptor Spring para identificar e configurar o contexto do tenant
- Datasource routing dinâmico por requisição

---

## 📊 Modelo de Dados (MVP)

### Entidades do Schema `public`:

- **Tenant**: Cadastro dos restaurantes
- **TenantUser**: Usuários com acesso aos tenants (pode ter acesso a múltiplos)

### Entidades por Tenant (schemas individuais):

- **User**: Usuários do restaurante (garçons, gerentes, cozinha)
- **Role**: Papéis/funções (OWNER, MANAGER, WAITER, KITCHEN, CASHIER)
- **Permission**: Permissões granulares
- **Category**: Categorias de produtos/pratos
- **Product**: Produtos (ingredientes e pratos finais)
- **Recipe**: Receitas dos pratos
- **RecipeIngredient**: Ingredientes de cada receita (N:N com quantidade)
- **Stock**: Controle de estoque
- **StockMovement**: Histórico de movimentações
- **Table**: Mesas do restaurante
- **Order**: Pedidos/Comandas
- **OrderItem**: Itens do pedido
- **Supplier**: Fornecedores (futuro)
- **Purchase**: Compras de fornecedores (futuro)

---

## 🔐 Sistema de Autenticação e Autorização

### Roles Principais:

1. **OWNER**: Dono do restaurante (acesso total)
2. **MANAGER**: Gerente (administração geral, sem finanças sensíveis)
3. **WAITER**: Garçom (pedidos, mesas, consultas)
4. **KITCHEN**: Cozinha (visualizar pedidos, atualizar status)
5. **CASHIER**: Caixa (fechamento de contas, relatórios financeiros)

### Fluxo de Autenticação:

1. Login com email/password
2. Validação do tenant
3. Geração de JWT com claims (userId, tenantId, roles)
4. Todas as requisições validam JWT + tenant context

### Policies:

- Verificação granular de permissões por endpoint
- Annotations customizadas: `@RequiresTenant`, `@RequiresRole`, `@RequiresPermission`

---

## 📁 Estrutura de Pastas (Clean Architecture)
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── restaurant/
│   │           └── saas/
│   │               ├── RestaurantSaasApplication.java
│   │               ├── config/              # Configurações Spring
│   │               │   ├── SecurityConfig.java
│   │               │   ├── MultiTenantConfig.java
│   │               │   └── SwaggerConfig.java
│   │               ├── domain/              # Entidades/Models
│   │               │   ├── entities/
│   │               │   ├── enums/
│   │               │   └── exceptions/
│   │               ├── application/         # Casos de Uso/Services
│   │               │   ├── services/
│   │               │   ├── dto/
│   │               │   └── mappers/
│   │               ├── infrastructure/      # Implementações técnicas
│   │               │   ├── persistence/     # Repositories
│   │               │   ├── security/        # JWT, filters
│   │               │   └── multitenant/     # Tenant resolution
│   │               └── presentation/        # Controllers/API
│   │                   ├── controllers/
│   │                   ├── requests/
│   │                   └── responses/
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
│       └── db/
│           └── migration/              # Flyway migrations
└── test/
    └── java/
        └── com/
            └── restaurant/
                └── saas/
                    ├── unit/
                    └── integration/
```

---

## 🚀 Como Executar

### Pré-requisitos

- Java 21 (OpenJDK)
- Maven 3.8+
- PostgreSQL 15+
- Git

### Setup do Ambiente

1. **Clone o repositório**
```bash
   git clone git@github.com:SEU_USUARIO/restaurant-saas.git
   cd restaurant-saas
```

2. **Configure o PostgreSQL**
```bash
   # Criar database
   sudo -u postgres psql -c "CREATE DATABASE restaurant_saas;"
   
   # Ou via psql
   psql -U postgres -h localhost
   CREATE DATABASE restaurant_saas;
   \q
```

3. **Configure as variáveis de ambiente (opcional)**
```bash
   export DB_USERNAME=postgres
   export DB_PASSWORD=sua_senha
```

4. **Execute as migrations**
```bash
   ./mvnw flyway:migrate
```

5. **Compile o projeto**
```bash
   ./mvnw clean compile
```

6. **Execute o projeto**
```bash
   ./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 📋 Funcionalidades do MVP

### Fase 1: Fundação
- [x] Configuração inicial do projeto Spring Boot
- [x] Configuração do PostgreSQL e Flyway
- [ ] Implementação do Multi-tenancy (schema per tenant)
- [ ] Sistema de autenticação JWT
- [ ] Sistema de roles e permissions

### Fase 2: Gestão Básica
- [ ] CRUD de Categorias
- [ ] CRUD de Produtos/Ingredientes
- [ ] CRUD de Receitas (com ingredientes)
- [ ] Controle de Estoque
- [ ] Alertas de estoque baixo

### Fase 3: Operação do Restaurante
- [ ] Gestão de Mesas
- [ ] Sistema de Pedidos
- [ ] Atualização de status de pedidos
- [ ] Cálculo automático de custos por receita
- [ ] Fechamento de contas

### Fase 4: Relatórios e Analytics
- [ ] Relatório de vendas
- [ ] Relatório de estoque
- [ ] Produtos mais vendidos
- [ ] Análise de margem de lucro
- [ ] Dashboard gerencial

---

## 🔧 Padrões e Boas Práticas

### Código
- **Clean Code**: Nomes significativos, funções pequenas, SRP
- **SOLID Principles**
- **Design Patterns**: Repository, Factory, Builder, Strategy
- **DTOs**: Nunca expor entidades diretamente nas APIs
- **Validation**: Bean Validation (jakarta.validation)
- **Exception Handling**: `@ControllerAdvice` para tratamento global

### Banco de Dados
- **Migrations**: Versionamento com Flyway
- **Naming Convention**: snake_case para tabelas/colunas
- **Indexes**: Campos de busca e foreign keys
- **Constraints**: NOT NULL, UNIQUE, CHECK onde aplicável

### API REST
- Padrão RESTful
- Versionamento: `/api/v1/`
- Status Codes apropriados
- Pagination para listagens
- Filtering e Sorting
- HATEOAS (opcional/avançado)

### Segurança
- Validação de entrada sempre
- SQL Injection protection (JPA/Hibernate)
- XSS Protection
- CORS configurado corretamente
- Rate Limiting (futuro)

### Testes
- Cobertura mínima: 70%
- Testes Unitários: Services e Utils
- Testes de Integração: Controllers e Repositories
- Test Containers: Para testes com PostgreSQL real

---

## 📚 Referências de Estudo

### Documentação Oficial
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)

### Multi-tenancy
- [Multi-tenancy with Spring Boot](https://www.baeldung.com/spring-boot-multitenancy)
- [Schema-based Multi-tenancy](https://medium.com/@joeclever/multi-tenancy-architecture-with-spring-boot-jpa-5c4ecae5e4b0)

### Arquitetura
- Clean Architecture - Robert C. Martin
- [Hexagonal Architecture](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/)

---

## 🐛 Troubleshooting Comum

### Problema: Multi-tenant não está funcionando
- Verificar se o TenantContext está sendo populado no interceptor
- Conferir se o schema do tenant existe no PostgreSQL
- Validar logs de routing do DataSource

### Problema: JWT inválido
- Verificar secret key e algoritmo
- Checar expiração do token
- Validar estrutura dos claims

### Problema: LazyInitializationException
- Usar `@Transactional` nos métodos de service
- Considerar fetch joins ou `@EntityGraph`
- Avaliar DTOs para evitar lazy loading

---

## 🚀 Próximos Passos (Pós-MVP)

- [ ] Sistema de delivery integrado
- [ ] Aplicativo mobile para garçons (React Native?)
- [ ] Dashboard em tempo real (WebSockets)
- [ ] Integração com pagamentos (Stripe, Mercado Pago)
- [ ] Relatórios avançados com gráficos
- [ ] Sistema de avaliações/feedback
- [ ] Integração com sistemas fiscais
- [ ] App para clientes (cardápio digital)

---

## 👨‍💻 Desenvolvedor

**Pedro** - Fullstack Developer

- Experiência prévia: Laravel, React, Next.js, Node.js
- Ambiente: Ubuntu 24.04 LTS
- Objetivo: Dominar Spring Boot em projetos SaaS complexos

---

## 📝 Licença

Projeto de estudo pessoal - uso educacional.

---

## 📅 Última Atualização

Dezembro de 2024