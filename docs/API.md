# API Documentation - Blog Pessoal

## 📋 Visão Geral

Esta documentação detalha todos os endpoints disponíveis na API REST do Blog Pessoal, incluindo exemplos de requisições e respostas.

## 🔑 Autenticação

A API utiliza autenticação básica HTTP. Para acessar endpoints protegidos, você deve:

1. Primeiro, cadastrar um usuário via `/usuarios/cadastrar`
2. Fazer login via `/usuarios/logar` para obter as credenciais
3. Incluir as credenciais em requisições subsequentes

### Headers Necessários
```
Content-Type: application/json
Authorization: Basic <encoded-credentials>
```

## 👤 Usuários

### POST /usuarios/cadastrar
Cadastra um novo usuário no sistema.

**Request Body:**
```json
{
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "password": "12345678",
  "photo": "https://exemplo.com/foto.jpg"
}
```

**Response 201 Created:**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "password": "$2a$10$...", // senha criptografada
  "photo": "https://exemplo.com/foto.jpg",
  "postagem": []
}
```

**Validações:**
- `name`: Obrigatório
- `email`: Obrigatório e deve ser um email válido
- `password`: Obrigatório, mínimo 8 caracteres
- `photo`: Opcional, máximo 5000 caracteres

### POST /usuarios/logar
Realiza o login do usuário.

**Request Body:**
```json
{
  "email": "joao.silva@email.com",
  "password": "12345678"
}
```

**Response 200 OK:**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "password": "",
  "photo": "https://exemplo.com/foto.jpg",
  "token": "Basic am9hby5zaWx2YUBlbWFpbC5jb206MTIzNDU2Nzg="
}
```

### GET /usuarios/all
Lista todos os usuários cadastrados.

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "password": "$2a$10$...",
    "photo": "https://exemplo.com/foto.jpg",
    "postagem": [
      {
        "id": 1,
        "titulo": "Minha primeira postagem",
        "texto": "Este é o conteúdo da minha primeira postagem...",
        "data": "2024-01-15T10:30:00"
      }
    ]
  }
]
```

### GET /usuarios/{id}
Busca um usuário específico por ID.

**Path Parameters:**
- `id` (Long): ID do usuário

**Response 200 OK:**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "password": "$2a$10$...",
  "photo": "https://exemplo.com/foto.jpg",
  "postagem": []
}
```

**Response 404 Not Found:**
```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "path": "/usuarios/999"
}
```

### PUT /usuarios/atualizar
Atualiza os dados de um usuário existente.

**Request Body:**
```json
{
  "id": 1,
  "name": "João Silva Santos",
  "email": "joao.silva@email.com",
  "password": "novaSenha123",
  "photo": "https://exemplo.com/nova-foto.jpg"
}
```

**Response 200 OK:**
```json
{
  "id": 1,
  "name": "João Silva Santos",
  "email": "joao.silva@email.com",
  "password": "$2a$10$...", // nova senha criptografada
  "photo": "https://exemplo.com/nova-foto.jpg",
  "postagem": []
}
```

## 📝 Postagens

### GET /postagens
Lista todas as postagens.

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "titulo": "Minha primeira postagem",
    "texto": "Este é o conteúdo da minha primeira postagem sobre tecnologia...",
    "data": "2024-01-15T10:30:00",
    "tema": {
      "id": 1,
      "descricao": "Tecnologia"
    },
    "usuario": {
      "id": 1,
      "name": "João Silva",
      "email": "joao.silva@email.com",
      "photo": "https://exemplo.com/foto.jpg"
    }
  }
]
```

### GET /postagens/{id}
Busca uma postagem específica por ID.

**Path Parameters:**
- `id` (Long): ID da postagem

**Response 200 OK:**
```json
{
  "id": 1,
  "titulo": "Minha primeira postagem",
  "texto": "Este é o conteúdo da minha primeira postagem sobre tecnologia...",
  "data": "2024-01-15T10:30:00",
  "tema": {
    "id": 1,
    "descricao": "Tecnologia"
  },
  "usuario": {
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "photo": "https://exemplo.com/foto.jpg"
  }
}
```

### GET /postagens/titulo/{titulo}
Busca postagens que contenham o título especificado (case-insensitive).

**Path Parameters:**
- `titulo` (String): Texto a ser pesquisado no título

**Exemplo:** `/postagens/titulo/tecnologia`

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "titulo": "Tecnologia e Inovação",
    "texto": "Artigo sobre as novidades em tecnologia...",
    "data": "2024-01-15T10:30:00",
    "tema": {
      "id": 1,
      "descricao": "Tecnologia"
    },
    "usuario": {
      "id": 1,
      "name": "João Silva",
      "email": "joao.silva@email.com"
    }
  }
]
```

### POST /postagens
Cria uma nova postagem.

**Request Body:**
```json
{
  "titulo": "Nova postagem sobre Spring Boot",
  "texto": "Neste artigo vou explicar como desenvolver uma API REST com Spring Boot...",
  "tema": {
    "id": 1
  },
  "usuario": {
    "id": 1
  }
}
```

**Response 201 Created:**
```json
{
  "id": 2,
  "titulo": "Nova postagem sobre Spring Boot",
  "texto": "Neste artigo vou explicar como desenvolver uma API REST com Spring Boot...",
  "data": "2024-01-15T14:20:00",
  "tema": {
    "id": 1,
    "descricao": "Tecnologia"
  },
  "usuario": {
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com"
  }
}
```

**Validações:**
- `titulo`: Obrigatório, 5-100 caracteres
- `texto`: Obrigatório, 10-1000 caracteres
- `tema`: Deve referenciar um tema existente
- `usuario`: Deve referenciar um usuário existente

### PUT /postagens
Atualiza uma postagem existente.

**Request Body:**
```json
{
  "id": 2,
  "titulo": "Postagem atualizada sobre Spring Boot",
  "texto": "Conteúdo atualizado da postagem sobre Spring Boot e suas funcionalidades...",
  "tema": {
    "id": 1
  },
  "usuario": {
    "id": 1
  }
}
```

**Response 200 OK:**
```json
{
  "id": 2,
  "titulo": "Postagem atualizada sobre Spring Boot",
  "texto": "Conteúdo atualizado da postagem sobre Spring Boot e suas funcionalidades...",
  "data": "2024-01-15T14:25:00", // data atualizada automaticamente
  "tema": {
    "id": 1,
    "descricao": "Tecnologia"
  },
  "usuario": {
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com"
  }
}
```

### DELETE /postagens/{id}
Exclui uma postagem.

**Path Parameters:**
- `id` (Long): ID da postagem a ser excluída

**Response 204 No Content** (sucesso)

**Response 404 Not Found** (postagem não encontrada)

## 🏷️ Temas

### GET /temas
Lista todos os temas.

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "descricao": "Tecnologia",
    "postagens": [
      {
        "id": 1,
        "titulo": "Minha primeira postagem",
        "texto": "Conteúdo sobre tecnologia...",
        "data": "2024-01-15T10:30:00"
      }
    ]
  },
  {
    "id": 2,
    "descricao": "Lifestyle",
    "postagens": []
  }
]
```

### GET /temas/{id}
Busca um tema específico por ID.

**Path Parameters:**
- `id` (Long): ID do tema

**Response 200 OK:**
```json
{
  "id": 1,
  "descricao": "Tecnologia",
  "postagens": [
    {
      "id": 1,
      "titulo": "Minha primeira postagem",
      "texto": "Conteúdo sobre tecnologia...",
      "data": "2024-01-15T10:30:00",
      "usuario": {
        "id": 1,
        "name": "João Silva"
      }
    }
  ]
}
```

### GET /temas/descricao/{descricao}
Busca temas que contenham a descrição especificada (case-insensitive).

**Path Parameters:**
- `descricao` (String): Texto a ser pesquisado na descrição

**Exemplo:** `/temas/descricao/tech`

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "descricao": "Tecnologia",
    "postagens": []
  }
]
```

### POST /temas
Cria um novo tema.

**Request Body:**
```json
{
  "descricao": "Ciência"
}
```

**Response 201 Created:**
```json
{
  "id": 3,
  "descricao": "Ciência",
  "postagens": []
}
```

**Validações:**
- `descricao`: Obrigatório

### PUT /temas
Atualiza um tema existente.

**Request Body:**
```json
{
  "id": 3,
  "descricao": "Ciência e Tecnologia"
}
```

**Response 200 OK:**
```json
{
  "id": 3,
  "descricao": "Ciência e Tecnologia",
  "postagens": []
}
```

### DELETE /temas/{id}
Exclui um tema.

**Path Parameters:**
- `id` (Long): ID do tema a ser excluído

**Response 204 No Content** (sucesso)

**Response 404 Not Found** (tema não encontrado)

⚠️ **Atenção:** Excluir um tema também excluirá todas as postagens associadas devido ao `CascadeType.REMOVE`.

## 🚨 Códigos de Status HTTP

| Código | Descrição | Quando Ocorre |
|--------|-----------|---------------|
| 200 | OK | Requisição bem-sucedida |
| 201 | Created | Recurso criado com sucesso |
| 204 | No Content | Recurso excluído com sucesso |
| 400 | Bad Request | Dados inválidos na requisição |
| 401 | Unauthorized | Autenticação necessária |
| 403 | Forbidden | Acesso negado |
| 404 | Not Found | Recurso não encontrado |
| 500 | Internal Server Error | Erro interno do servidor |

## ❌ Exemplos de Erros

### Erro de Validação (400 Bad Request)
```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": [
    {
      "field": "titulo",
      "message": "O tamanho minimo é de 05 caracteres e o máximo é 100 caracteres"
    },
    {
      "field": "email",
      "message": "O usuário precisa ser um email válido"
    }
  ]
}
```

### Recurso Não Encontrado (404 Not Found)
```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Postagem não encontrada",
  "path": "/postagens/999"
}
```

## 🔧 Configuração para Testes

### Swagger UI
Acesse `http://localhost:8080/swagger-ui.html` para uma interface gráfica completa para testar todos os endpoints.

### Postman/Insomnia
Importe a coleção de endpoints usando a URL do OpenAPI:
`http://localhost:8080/v3/api-docs`

### cURL Examples

**Cadastrar usuário:**
```bash
curl -X POST http://localhost:8080/usuarios/cadastrar \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@email.com",
    "password": "12345678",
    "photo": "https://exemplo.com/foto.jpg"
  }'
```

**Fazer login:**
```bash
curl -X POST http://localhost:8080/usuarios/logar \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@email.com",
    "password": "12345678"
  }'
```

**Criar postagem (com autenticação):**
```bash
curl -X POST http://localhost:8080/postagens \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic am9hb0BlbWFpbC5jb206MTIzNDU2Nzg=" \
  -d '{
    "titulo": "Minha postagem",
    "texto": "Conteúdo da postagem aqui...",
    "tema": {"id": 1},
    "usuario": {"id": 1}
  }'
```

## 📝 Notas Importantes

1. **CORS**: A API está configurada para aceitar requisições de qualquer origem (`*`)
2. **Timestamps**: Todas as datas são retornadas no formato ISO 8601
3. **Cascata**: Excluir usuários ou temas também exclui suas postagens relacionadas
4. **Validação**: Todos os campos obrigatórios são validados antes da persistência
5. **Segurança**: Senhas são automaticamente criptografadas antes de serem salvas
6. **Relacionamentos**: Os objetos relacionados são retornados com informações básicas para evitar referências circulares