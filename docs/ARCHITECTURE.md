# Arquitetura do Sistema - Blog Pessoal

## 🏗️ Visão Geral da Arquitetura

O Blog Pessoal foi desenvolvido seguindo os princípios da **Arquitetura em Camadas** e padrões do **Spring Boot**, implementando uma API REST robusta e escalável.

```
┌─────────────────────────────────────────────────────────────┐
│                      CLIENTE (Frontend)                     │
│                    React, Angular, Vue                      │
└─────────────────────┬───────────────────────────────────────┘
                      │ HTTP/HTTPS
                      │ JSON
┌─────────────────────▼───────────────────────────────────────┐
│                   CAMADA DE APRESENTAÇÃO                    │
│                     Controllers                             │
│   ┌─────────────┐ ┌─────────────┐ ┌─────────────────────┐   │
│   │   Usuario   │ │  Postagem   │ │       Temas        │   │
│   │ Controller  │ │ Controller  │ │    Controller       │   │
│   └─────────────┘ └─────────────┘ └─────────────────────┘   │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                   CAMADA DE SEGURANÇA                       │
│               Spring Security + JWT                         │
│   ┌─────────────────────┐ ┌─────────────────────────────┐   │
│   │ BasicSecurityConfig │ │   UserDetailsService        │   │
│   └─────────────────────┘ └─────────────────────────────┘   │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                   CAMADA DE NEGÓCIO                         │
│                      Services                               │
│                ┌─────────────────┐                          │
│                │ UsuarioService  │                          │
│                └─────────────────┘                          │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                  CAMADA DE PERSISTÊNCIA                     │
│                   Repositories + JPA                        │
│   ┌─────────────┐ ┌─────────────┐ ┌─────────────────────┐   │
│   │   Usuario   │ │  Postagem   │ │       Temas        │   │
│   │ Repository  │ │ Repository  │ │    Repository       │   │
│   └─────────────┘ └─────────────┘ └─────────────────────┘   │
└─────────────────────┬───────────────────────────────────────┘
                      │ JPA/Hibernate
┌─────────────────────▼───────────────────────────────────────┐
│                    BANCO DE DADOS                           │
│              MySQL / PostgreSQL / H2                       │
│                                                             │
│   ┌─────────────┐ ┌─────────────┐ ┌─────────────────────┐   │
│   │ tb_usuario  │ │tb_postagens │ │     tb_temas        │   │
│   └─────────────┘ └─────────────┘ └─────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

## 📁 Estrutura do Projeto

### Organização de Pacotes

```
com.generation.blogpessoal/
├── BlogpessoalApplication.java      # Classe principal
├── configuration/                   # Configurações
│   └── SwaggerConfig.java          # Config do Swagger/OpenAPI
├── controller/                      # Camada de Apresentação
│   ├── PostagemController.java
│   ├── TemasController.java
│   └── UsuarioController.java
├── model/                          # Camada de Modelo/Entidades
│   ├── Postagem.java
│   ├── Temas.java
│   ├── Usuario.java
│   └── UsuarioLogin.java           # DTO para login
├── repository/                     # Camada de Persistência
│   ├── PostagemRepository.java
│   ├── TemasRepository.java
│   └── UsuarioRepository.java
├── security/                       # Configurações de Segurança
│   ├── BasciSecurityConfig.java
│   ├── UserDetailsServiceimpl.java
│   └── UserDetailslmpl.java
└── service/                        # Camada de Negócio
    └── UsuarioService.java
```

## 🔄 Padrões Arquiteturais Utilizados

### 1. **MVC (Model-View-Controller)**
- **Model**: Entidades JPA (`Usuario`, `Postagem`, `Temas`)
- **View**: Respostas JSON da API
- **Controller**: Classes de controle que expõem endpoints REST

### 2. **Repository Pattern**
- Abstração do acesso a dados
- Interfaces que estendem `JpaRepository`
- Métodos de consulta customizados usando `@Query`

### 3. **Dependency Injection**
- Inversão de controle através do Spring
- Uso de `@Autowired` para injeção de dependências
- Baixo acoplamento entre camadas

### 4. **DTO (Data Transfer Object)**
- `UsuarioLogin` para transferência segura de dados de login
- Evita exposição de entidades completas

## 🗃️ Modelo de Dados

### Diagrama ER (Entity Relationship)

```
┌─────────────────────┐
│       USUARIO       │
├─────────────────────┤
│ id (PK)             │
│ name                │
│ email (UNIQUE)      │
│ password            │
│ photo               │
└──────────┬──────────┘
           │
           │ 1:N
           │
┌──────────▼──────────┐
│      POSTAGEM       │
├─────────────────────┤
│ id (PK)             │
│ titulo              │
│ texto               │
│ data                │
│ usuario_id (FK)     │
│ tema_id (FK)        │
└──────────┬──────────┘
           │
           │ N:1
           │
┌──────────▼──────────┐
│        TEMA         │
├─────────────────────┤
│ id (PK)             │
│ descricao           │
└─────────────────────┘
```

### Relacionamentos

1. **Usuario ↔ Postagem** (1:N)
   - Um usuário pode ter várias postagens
   - Uma postagem pertence a apenas um usuário
   - Cascade: `REMOVE` (excluir usuário exclui suas postagens)

2. **Tema ↔ Postagem** (1:N)
   - Um tema pode ter várias postagens
   - Uma postagem pertence a apenas um tema
   - Cascade: `REMOVE` (excluir tema exclui suas postagens)

## 🔧 Tecnologias e Frameworks

### Core Framework
- **Spring Boot 3.0.5**: Framework principal
- **Spring Web**: Para criação de APIs REST
- **Spring Data JPA**: Persistência de dados
- **Spring Security**: Autenticação e autorização
- **Spring Validation**: Validação de dados

### Persistência
- **Hibernate**: ORM para mapeamento objeto-relacional
- **JPA 3.0**: Especificação para persistência
- **H2**: Banco em memória para desenvolvimento
- **MySQL Connector**: Driver para MySQL
- **PostgreSQL Driver**: Driver para PostgreSQL

### Documentação
- **SpringDoc OpenAPI 3**: Geração automática de documentação
- **Swagger UI**: Interface para teste da API

### Build e Deploy
- **Maven**: Gerenciamento de dependências e build
- **Docker**: Containerização
- **Spring Boot DevTools**: Hot reload em desenvolvimento

## 🔐 Arquitetura de Segurança

### Camadas de Segurança

```
┌─────────────────────────────────────────────────────────────┐
│                      REQUEST                                │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                  CORS FILTER                                │
│              Cross-Origin Resource Sharing                  │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│              AUTHENTICATION FILTER                          │
│                 BasicAuthenticationFilter                   │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                AUTHORIZATION                                │
│              Role-based Access Control                      │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                   CONTROLLER                                │
│                  Protected Resource                         │
└─────────────────────────────────────────────────────────────┘
```

### Componentes de Segurança

1. **BasicSecurityConfig**
   - Configuração principal de segurança
   - Define URLs públicas vs protegidas
   - Configura CORS
   - Habilita autenticação HTTP Basic

2. **UserDetailsServiceImpl**
   - Carrega dados do usuário para autenticação
   - Busca usuário por email no banco de dados
   - Converte `Usuario` para `UserDetails`

3. **UserDetailsImpl**
   - Implementação de `UserDetails`
   - Wrapper para entidade `Usuario`
   - Define authorities/roles do usuário

### Fluxo de Autenticação

```
1. Cliente envia credenciais (email/senha)
        ↓
2. BasicAuthenticationFilter intercepta
        ↓
3. UserDetailsService carrega usuário do BD
        ↓
4. Senhas são comparadas (hash vs plain)
        ↓
5. Se válido, cria Authentication object
        ↓
6. Request prossegue para o Controller
```

## 📊 Fluxo de Dados

### Ciclo de Vida de uma Requisição

```
[CLIENT] → [CONTROLLER] → [SERVICE] → [REPOSITORY] → [DATABASE]
    ↑                                                      ↓
    └── [JSON Response] ← [Entity/DTO] ← [Entity] ← [Result Set]
```

### Exemplo: Criar Postagem

1. **Cliente** envia POST para `/postagens`
2. **PostagemController** recebe e valida dados
3. **Repository** persiste no banco via JPA
4. **Hibernate** gera SQL e executa
5. **Database** confirma inserção
6. **Controller** retorna postagem criada como JSON

## 🚀 Configurações de Perfil

### Perfis Disponíveis

#### Development (`dev`)
```properties
# H2 em memória
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
spring.jpa.show-sql=true
logging.level.sql=DEBUG
```

#### Production (`prod`)
```properties
# MySQL/PostgreSQL
spring.datasource.url=jdbc:mysql://localhost/db_blogpessoal
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
logging.level.root=WARN
```

## 🔄 Escalabilidade e Performance

### Estratégias Implementadas

1. **Connection Pooling**
   - HikariCP (padrão do Spring Boot)
   - Pool de conexões otimizado

2. **JPA Optimizations**
   - Lazy loading configurado
   - `@JsonIgnoreProperties` para evitar referências circulares

3. **Validation**
   - Bean Validation para entrada de dados
   - Validação no nível de entidade

### Possíveis Melhorias

1. **Caching**
   ```java
   @Cacheable("temas")
   public List<Temas> findAll() { ... }
   ```

2. **Pagination**
   ```java
   public Page<Postagem> findAll(Pageable pageable)
   ```

3. **Database Indexing**
   ```sql
   CREATE INDEX idx_postagem_titulo ON tb_postagens(titulo);
   CREATE INDEX idx_usuario_email ON tb_usuario(email);
   ```

## 🧪 Arquitetura de Testes

### Estrutura de Testes

```
src/test/java/
├── unit/                    # Testes unitários
│   ├── controller/
│   ├── service/
│   └── repository/
├── integration/             # Testes de integração
│   └── api/
└── e2e/                    # Testes end-to-end
    └── scenarios/
```

### Estratégias de Teste

1. **Unit Tests**
   - Mockito para mock de dependências
   - JUnit 5 como framework base
   - Testes isolados de cada camada

2. **Integration Tests**
   - `@SpringBootTest` para contexto completo
   - `@TestPropertySource` para configurações de teste
   - Banco H2 para testes

3. **API Tests**
   - `@WebMvcTest` para controllers
   - MockMvc para simulação de requests
   - `@JsonTest` para serialização

## 📐 Princípios SOLID Aplicados

### Single Responsibility Principle (SRP)
- Cada classe tem uma única responsabilidade
- Controllers apenas expõem endpoints
- Services contêm lógica de negócio
- Repositories apenas acesso a dados

### Open/Closed Principle (OCP)
- Interfaces de Repository permitem extensão
- Configurações podem ser sobrescritas

### Liskov Substitution Principle (LSP)
- Implementações de UserDetails são intercambiáveis
- Repositories implementam contratos bem definidos

### Interface Segregation Principle (ISP)
- Interfaces pequenas e específicas
- UserDetails apenas com métodos necessários

### Dependency Inversion Principle (DIP)
- Controllers dependem de abstrações (interfaces)
- Injeção de dependência via Spring

## 🔮 Considerações para Evolução

### Próximas Funcionalidades

1. **Microservices**
   - Separar usuários, postagens e temas em serviços independentes
   - Service discovery com Eureka
   - API Gateway com Spring Cloud Gateway

2. **Event-Driven Architecture**
   - Spring Cloud Stream para eventos
   - RabbitMQ ou Apache Kafka como message broker
   - Eventos de criação/edição de postagens

3. **CQRS (Command Query Responsibility Segregation)**
   - Separar operações de leitura e escrita
   - Read models otimizados
   - Event sourcing para auditoria

4. **Advanced Security**
   - JWT tokens em vez de Basic Auth
   - OAuth2 integration
   - Rate limiting
   - API versioning

---

Esta arquitetura fornece uma base sólida e escalável para o desenvolvimento do Blog Pessoal, seguindo as melhores práticas do ecossistema Spring e permitindo evolução futura conforme os requisitos do negócio.