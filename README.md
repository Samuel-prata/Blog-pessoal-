# Blog Pessoal - API REST

## 📝 Descrição

O **Blog Pessoal** é uma API REST desenvolvida em Spring Boot que simula o backend de um blog pessoal. A aplicação permite que usuários se cadastrem, façam login e criem postagens organizadas por temas. É um projeto educacional que demonstra conceitos fundamentais de desenvolvimento web com Java, Spring Boot e persistência de dados.

## 🚀 Funcionalidades

- **Gestão de Usuários**: Cadastro, login e autenticação de usuários
- **Gestão de Postagens**: Criação, edição, visualização e exclusão de postagens
- **Gestão de Temas**: Organização de postagens por temas/categorias
- **Autenticação e Segurança**: Sistema de autenticação com Spring Security
- **Documentação API**: Interface Swagger/OpenAPI para teste e documentação dos endpoints
- **Validação de Dados**: Validação robusta de entrada de dados
- **Suporte Multi-ambiente**: Configurações para desenvolvimento e produção

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 3.0.5** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **Spring Validation** - Validação de dados
- **Hibernate** - ORM (Object-Relational Mapping)

### Banco de Dados
- **MySQL** - Banco de dados principal (produção)
- **H2 Database** - Banco de dados em memória (desenvolvimento)
- **PostgreSQL** - Suporte para PostgreSQL

### Documentação e Testing
- **SpringDoc OpenAPI 3** - Documentação automática da API
- **Swagger UI** - Interface gráfica para teste da API
- **JUnit** - Framework de testes unitários

### DevOps
- **Docker** - Containerização da aplicação
- **Maven** - Gerenciamento de dependências e build

## 📊 Arquitetura

A aplicação segue o padrão **MVC (Model-View-Controller)** e está organizada em camadas:

```
src/main/java/com/generation/blogpessoal/
├── BlogpessoalApplication.java    # Classe principal da aplicação
├── configuration/                 # Configurações da aplicação
│   └── SwaggerConfig.java        # Configuração do Swagger
├── controller/                    # Controladores REST
│   ├── PostagemController.java   # Endpoints de postagens
│   ├── TemasController.java      # Endpoints de temas
│   └── UsuarioController.java    # Endpoints de usuários
├── model/                        # Entidades do banco de dados
│   ├── Postagem.java            # Entidade Postagem
│   ├── Temas.java               # Entidade Tema
│   ├── Usuario.java             # Entidade Usuário
│   └── UsuarioLogin.java        # DTO para login
├── repository/                   # Interfaces de acesso a dados
│   ├── PostagemRepository.java  # Repositório de postagens
│   ├── TemasRepository.java     # Repositório de temas
│   └── UsuarioRepository.java   # Repositório de usuários
├── security/                     # Configurações de segurança
│   ├── BasciSecurityConfig.java # Configuração principal de segurança
│   ├── UserDetailsServiceimpl.java # Implementação do UserDetailsService
│   └── UserDetailslmpl.java     # Implementação do UserDetails
└── service/                     # Lógica de negócio
    └── UsuarioService.java      # Serviços relacionados ao usuário
```

## 🗃️ Modelo de Dados

### Entidades Principais

#### Usuário
- **id**: Identificador único (Long)
- **name**: Nome do usuário (String)
- **email**: Email do usuário (String) - usado como login
- **password**: Senha criptografada (String)
- **photo**: URL da foto do perfil (String)
- **postagem**: Lista de postagens do usuário (One-to-Many)

#### Postagem
- **id**: Identificador único (Long)
- **titulo**: Título da postagem (String, 5-100 caracteres)
- **texto**: Conteúdo da postagem (String, 10-1000 caracteres)
- **data**: Data/hora de criação (LocalDateTime)
- **tema**: Tema da postagem (Many-to-One)
- **usuario**: Autor da postagem (Many-to-One)

#### Tema
- **id**: Identificador único (Long)
- **descricao**: Descrição do tema (String)
- **postagens**: Lista de postagens do tema (One-to-Many)

### Relacionamentos
- **Usuario** ←→ **Postagem**: Um usuário pode ter várias postagens (1:N)
- **Tema** ←→ **Postagem**: Um tema pode ter várias postagens (1:N)

## 🔗 Endpoints da API

### Autenticação
- `POST /usuarios/logar` - Realizar login
- `POST /usuarios/cadastrar` - Cadastrar novo usuário

### Usuários
- `GET /usuarios/all` - Listar todos os usuários
- `GET /usuarios/{id}` - Buscar usuário por ID
- `PUT /usuarios/atualizar` - Atualizar dados do usuário

### Postagens
- `GET /postagens` - Listar todas as postagens
- `GET /postagens/{id}` - Buscar postagem por ID
- `GET /postagens/titulo/{titulo}` - Buscar postagens por título
- `POST /postagens` - Criar nova postagem
- `PUT /postagens` - Atualizar postagem
- `DELETE /postagens/{id}` - Excluir postagem

### Temas
- `GET /temas` - Listar todos os temas
- `GET /temas/{id}` - Buscar tema por ID
- `GET /temas/descricao/{descricao}` - Buscar temas por descrição
- `POST /temas` - Criar novo tema
- `PUT /temas` - Atualizar tema
- `DELETE /temas/{id}` - Excluir tema

## ⚙️ Instalação e Configuração

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior
- MySQL 8.0 ou superior (para produção)
- Docker (opcional)

### Instalação Local

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd blogpessoal
```

2. **Configure o banco de dados**
   
   Para **MySQL** (produção), edite `src/main/resources/application-prod.properties`:
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database=mysql
spring.datasource.url=jdbc:mysql://localhost/db_blogpessoal?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo&useSSl=false
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

   Para **desenvolvimento** (H2), a aplicação já está configurada em `application-dev.properties`.

3. **Execute a aplicação**
```bash
# Usando Maven Wrapper
./mvnw spring-boot:run

# Ou usando Maven instalado
mvn spring-boot:run
```

4. **Acesse a aplicação**
   - API: `http://localhost:8080`
   - Documentação Swagger: `http://localhost:8080/swagger-ui.html`

### Instalação com Docker

1. **Build da imagem**
```bash
docker build -t blogpessoal:latest .
```

2. **Execute o container**
```bash
docker run -p 8080:8080 blogpessoal:latest
```

## 🔧 Configuração de Perfis

A aplicação suporta diferentes perfis de execução:

- **dev**: Desenvolvimento com H2 Database
- **prod**: Produção com MySQL/PostgreSQL

Para alterar o perfil, modifique `application.properties`:
```properties
spring.profiles.active=dev  # ou prod
```

## 📚 Documentação da API

A documentação completa da API está disponível através do Swagger UI:

- **URL**: `http://localhost:8080/swagger-ui.html`
- **JSON**: `http://localhost:8080/v3/api-docs`

A documentação inclui:
- Descrição detalhada de todos os endpoints
- Esquemas de request/response
- Exemplos de uso
- Códigos de status HTTP
- Interface para teste direto dos endpoints

## 🔐 Segurança

A aplicação implementa as seguintes medidas de segurança:

- **Autenticação**: Sistema de login com email/senha
- **Criptografia**: Senhas armazenadas com hash seguro
- **Validação**: Validação rigorosa de dados de entrada
- **CORS**: Configurado para permitir requisições de diferentes origens
- **Autorização**: Controle de acesso aos recursos

### Validações Implementadas

#### Usuário
- Nome obrigatório
- Email válido e obrigatório
- Senha mínima de 8 caracteres
- URL da foto limitada a 5000 caracteres

#### Postagem
- Título obrigatório (5-100 caracteres)
- Texto obrigatório (10-1000 caracteres)
- Data/hora automática

#### Tema
- Descrição obrigatória

## 🧪 Testes

Para executar os testes:

```bash
# Executar todos os testes
./mvnw test

# Executar testes com relatório de cobertura
./mvnw test jacoco:report
```

## 🚀 Deploy

### Ambiente de Produção

1. **Configure as variáveis de ambiente**:
```bash
export SPRING_PROFILES_ACTIVE=prod
export DATABASE_URL=jdbc:mysql://localhost/db_blogpessoal
export DATABASE_USERNAME=usuario_producao
export DATABASE_PASSWORD=senha_producao
```

2. **Gere o JAR de produção**:
```bash
./mvnw clean package -DskipTests
```

3. **Execute a aplicação**:
```bash
java -jar target/blogpessoal-0.0.1.jar
```

### Deploy com Docker

```bash
# Build
docker build -t blogpessoal:prod .

# Run
docker run -d \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DATABASE_URL=jdbc:mysql://host_mysql/db_blogpessoal \
  -e DATABASE_USERNAME=usuario \
  -e DATABASE_PASSWORD=senha \
  --name blogpessoal-app \
  blogpessoal:prod
```

## 📈 Monitoramento

A aplicação expõe métricas através do Spring Boot Actuator (quando habilitado):

- **Health Check**: `/actuator/health`
- **Métricas**: `/actuator/metrics`
- **Info**: `/actuator/info`

## 🤝 Contribuição

Para contribuir com o projeto:

1. Faça um fork do repositório
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto é um exemplo educacional e está disponível sob licença MIT.

## 👥 Autores

- **Generation Brasil** - Projeto educacional

## 📞 Suporte

Para dúvidas ou suporte:

- Abra uma issue no repositório
- Consulte a documentação do Swagger
- Verifique os logs da aplicação

---

**Nota**: Este é um projeto educacional desenvolvido como parte do bootcamp da Generation Brasil. Não deve ser usado em ambiente de produção sem as devidas adaptações de segurança e performance.