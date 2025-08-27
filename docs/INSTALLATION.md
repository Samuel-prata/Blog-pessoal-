# Guia de Instalação - Blog Pessoal

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado em seu sistema:

### Obrigatórios
- **Java 17 ou superior** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6 ou superior** - [Download](https://maven.apache.org/download.cgi)
- **Git** - [Download](https://git-scm.com/downloads)

### Opcionais (para diferentes cenários)
- **MySQL 8.0+** - Para ambiente de produção
- **Docker** - Para containerização
- **PostgreSQL** - Alternativa de banco de dados
- **IDE** - IntelliJ IDEA, Eclipse, VS Code

## 🚀 Instalação Rápida (Ambiente de Desenvolvimento)

### 1. Clone o Repositório
```bash
git clone <url-do-repositorio>
cd blogpessoal
```

### 2. Execute a Aplicação
```bash
# Usando Maven Wrapper (recomendado)
./mvnw spring-boot:run

# Ou usando Maven instalado
mvn spring-boot:run
```

### 3. Acesse a Aplicação
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console

A aplicação será executada com:
- Perfil de desenvolvimento (`dev`)
- Banco H2 em memória
- Dados de teste pré-carregados

## 🗃️ Configuração de Banco de Dados

### Ambiente de Desenvolvimento (H2)

O H2 já está configurado e funcionará automaticamente. Para acessar o console:

1. Acesse: http://localhost:8080/h2-console
2. Configure:
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **User Name**: `sa`
   - **Password**: (deixe em branco)

### Ambiente de Produção (MySQL)

#### 1. Instalar MySQL

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

**CentOS/RHEL:**
```bash
sudo yum install mysql-server
sudo systemctl start mysqld
sudo mysql_secure_installation
```

**Windows/macOS:**
Baixe o instalador oficial do [MySQL](https://dev.mysql.com/downloads/installer/)

#### 2. Criar Banco de Dados
```sql
mysql -u root -p

CREATE DATABASE db_blogpessoal;
CREATE USER 'bloguser'@'localhost' IDENTIFIED BY 'blogpass123';
GRANT ALL PRIVILEGES ON db_blogpessoal.* TO 'bloguser'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### 3. Configurar Aplicação
Edite `src/main/resources/application-prod.properties`:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database=mysql
spring.datasource.url=jdbc:mysql://localhost/db_blogpessoal?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo&useSSl=false
spring.datasource.username=bloguser
spring.datasource.password=blogpass123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

#### 4. Executar com Perfil de Produção
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### PostgreSQL (Alternativo)

#### 1. Instalar PostgreSQL

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
```

#### 2. Configurar Banco
```bash
sudo -u postgres createdb db_blogpessoal
sudo -u postgres createuser --interactive bloguser
sudo -u postgres psql
ALTER USER bloguser PASSWORD 'blogpass123';
GRANT ALL PRIVILEGES ON DATABASE db_blogpessoal TO bloguser;
\q
```

#### 3. Configurar Aplicação
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database=postgresql
spring.datasource.url=jdbc:postgresql://localhost:5432/db_blogpessoal
spring.datasource.username=bloguser
spring.datasource.password=blogpass123
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## 🐳 Instalação com Docker

### 1. Build da Imagem
```bash
docker build -t blogpessoal:latest .
```

### 2. Executar Container (Desenvolvimento)
```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  --name blogpessoal-dev \
  blogpessoal:latest
```

### 3. Executar com MySQL via Docker Compose

Crie um arquivo `docker-compose.yml`:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: blogpessoal-mysql
    environment:
      MYSQL_ROOT_PASSWORD: rootpass
      MYSQL_DATABASE: db_blogpessoal
      MYSQL_USER: bloguser
      MYSQL_PASSWORD: blogpass123
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - blogpessoal-network

  app:
    build: .
    container_name: blogpessoal-app
    environment:
      SPRING_PROFILES_ACTIVE: prod
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/db_blogpessoal?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo&useSSl=false
      SPRING_DATASOURCE_USERNAME: bloguser
      SPRING_DATASOURCE_PASSWORD: blogpass123
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    networks:
      - blogpessoal-network

volumes:
  mysql_data:

networks:
  blogpessoal-network:
    driver: bridge
```

Execute:
```bash
docker-compose up -d
```

## ⚙️ Configurações Avançadas

### Variáveis de Ambiente

Você pode configurar a aplicação usando variáveis de ambiente:

```bash
export SPRING_PROFILES_ACTIVE=prod
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost/db_blogpessoal
export SPRING_DATASOURCE_USERNAME=bloguser
export SPRING_DATASOURCE_PASSWORD=blogpass123
export SERVER_PORT=8080
```

### Configuração de CORS

Para alterar as configurações de CORS, edite os controllers ou adicione uma configuração global:

```java
@Configuration
public class CorsConfig {
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

### SSL/HTTPS (Produção)

Para configurar HTTPS, adicione ao `application-prod.properties`:

```properties
server.port=8443
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=changeit
server.ssl.keyStoreType=PKCS12
server.ssl.keyAlias=tomcat
```

## 🧪 Verificação da Instalação

### 1. Health Check
```bash
curl http://localhost:8080/actuator/health
```

**Resposta esperada:**
```json
{"status":"UP"}
```

### 2. Teste de Endpoint
```bash
curl http://localhost:8080/temas
```

### 3. Swagger UI
Acesse: http://localhost:8080/swagger-ui.html

Você deve ver a documentação interativa da API.

## 🛠️ Troubleshooting

### Problemas Comuns

#### 1. Porta já em uso
```
***************************
APPLICATION FAILED TO START
***************************

Description:
Web server failed to start. Port 8080 was already in use.
```

**Solução:**
```bash
# Encontrar processo usando a porta
lsof -i :8080
# ou
netstat -tulpn | grep 8080

# Matar o processo
kill -9 <PID>

# Ou usar uma porta diferente
./mvnw spring-boot:run -Dserver.port=8081
```

#### 2. Erro de conexão com MySQL
```
com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure
```

**Soluções:**
1. Verificar se o MySQL está rodando:
   ```bash
   sudo systemctl status mysql
   ```

2. Verificar configurações de conexão no `application-prod.properties`

3. Testar conexão manualmente:
   ```bash
   mysql -h localhost -u bloguser -p db_blogpessoal
   ```

#### 3. Erro de versão do Java
```
Error: A JNI error has occurred, please check your installation and try again
Exception in thread "main" java.lang.UnsupportedClassVersionError
```

**Solução:**
Verificar versão do Java:
```bash
java -version
javac -version
```

Instalar Java 17 ou superior.

#### 4. Erro de Maven
```bash
# Limpar cache do Maven
./mvnw clean

# Forçar download de dependências
./mvnw clean install -U

# Verificar versão do Maven
./mvnw -version
```

#### 5. Problemas com H2 Console
Se não conseguir acessar o H2 Console:

1. Verificar se está usando perfil `dev`
2. Acessar: http://localhost:8080/h2-console
3. Configurar URL: `jdbc:h2:mem:testdb`

### Logs para Diagnóstico

#### Aumentar nível de log
Adicione ao `application.properties`:
```properties
logging.level.com.generation.blogpessoal=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

#### Ver logs em tempo real
```bash
tail -f logs/application.log
```

## 📚 Próximos Passos

Após a instalação bem-sucedida:

1. **Leia a [Documentação da API](API.md)** para entender os endpoints
2. **Explore o Swagger UI** para testar a API interativamente
3. **Configure um cliente frontend** (React, Angular, Vue.js)
4. **Implemente testes** para validar o funcionamento
5. **Configure CI/CD** para automatizar deploys

## 🆘 Suporte

Se encontrar problemas durante a instalação:

1. Verifique os logs da aplicação
2. Consulte a seção de troubleshooting
3. Abra uma issue no repositório
4. Verifique a documentação oficial do Spring Boot

## 📋 Checklist de Instalação

- [ ] Java 17+ instalado
- [ ] Maven 3.6+ instalado  
- [ ] Repositório clonado
- [ ] Aplicação executando na porta 8080
- [ ] Swagger UI acessível
- [ ] Banco de dados configurado (H2 para dev, MySQL/PostgreSQL para prod)
- [ ] Testes de endpoints funcionando
- [ ] Health check retornando status UP

---

**Nota:** Este guia assume conhecimento básico de linha de comando e desenvolvimento Java. Para usuários iniciantes, recomenda-se começar com o ambiente de desenvolvimento (H2) antes de configurar bancos de dados de produção.