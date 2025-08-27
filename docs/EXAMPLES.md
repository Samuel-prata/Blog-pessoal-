# Exemplos de Uso - Blog Pessoal API

## 🎯 Cenários Completos de Uso

Esta documentação apresenta exemplos práticos de como usar a API do Blog Pessoal, desde o cadastro até operações avançadas.

## 🚀 Cenário 1: Primeiros Passos

### 1.1 Cadastrar um Novo Usuário

**Request:**
```bash
curl -X POST http://localhost:8080/usuarios/cadastrar \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Silva",
    "email": "maria.silva@email.com",
    "password": "senha123456",
    "photo": "https://avatars.githubusercontent.com/maria"
  }'
```

**Response:**
```json
{
  "id": 1,
  "name": "Maria Silva",
  "email": "maria.silva@email.com",
  "password": "$2a$10$rQ7qZ...", // senha criptografada
  "photo": "https://avatars.githubusercontent.com/maria",
  "postagem": []
}
```

### 1.2 Fazer Login

**Request:**
```bash
curl -X POST http://localhost:8080/usuarios/logar \
  -H "Content-Type: application/json" \
  -d '{
    "email": "maria.silva@email.com",
    "password": "senha123456"
  }'
```

**Response:**
```json
{
  "id": 1,
  "name": "Maria Silva",
  "email": "maria.silva@email.com",
  "password": "",
  "photo": "https://avatars.githubusercontent.com/maria",
  "token": "Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOnNlbmhhMTIzNDU2"
}
```

> **💡 Dica:** Guarde o token retornado para usar nas próximas requisições autenticadas.

## 📝 Cenário 2: Gerenciando Temas

### 2.1 Criar Temas para Organizar Postagens

**Criar tema "Tecnologia":**
```bash
curl -X POST http://localhost:8080/temas \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOnNlbmhhMTIzNDU2" \
  -d '{
    "descricao": "Tecnologia"
  }'
```

**Response:**
```json
{
  "id": 1,
  "descricao": "Tecnologia",
  "postagens": []
}
```

**Criar mais temas:**
```bash
# Lifestyle
curl -X POST http://localhost:8080/temas \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOnNlbmhhMTIzNDU2" \
  -d '{"descricao": "Lifestyle"}'

# Viagens
curl -X POST http://localhost:8080/temas \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOnNlbmhhMTIzNDU2" \
  -d '{"descricao": "Viagens"}'
```

### 2.2 Listar Todos os Temas

**Request:**
```bash
curl http://localhost:8080/temas
```

**Response:**
```json
[
  {
    "id": 1,
    "descricao": "Tecnologia",
    "postagens": []
  },
  {
    "id": 2,
    "descricao": "Lifestyle",
    "postagens": []
  },
  {
    "id": 3,
    "descricao": "Viagens",
    "postagens": []
  }
]
```

## 📄 Cenário 3: Criando e Gerenciando Postagens

### 3.1 Criar Primeira Postagem

**Request:**
```bash
curl -X POST http://localhost:8080/postagens \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOnNlbmhhMTIzNDU2" \
  -d '{
    "titulo": "Introdução ao Spring Boot",
    "texto": "Spring Boot é um framework que simplifica o desenvolvimento de aplicações Java. Com suas configurações automáticas e convenções inteligentes, você pode criar APIs REST robustas rapidamente.",
    "tema": {
      "id": 1
    },
    "usuario": {
      "id": 1
    }
  }'
```

**Response:**
```json
{
  "id": 1,
  "titulo": "Introdução ao Spring Boot",
  "texto": "Spring Boot é um framework que simplifica o desenvolvimento de aplicações Java...",
  "data": "2024-01-15T14:30:25.123456",
  "tema": {
    "id": 1,
    "descricao": "Tecnologia"
  },
  "usuario": {
    "id": 1,
    "name": "Maria Silva",
    "email": "maria.silva@email.com",
    "photo": "https://avatars.githubusercontent.com/maria"
  }
}
```

### 3.2 Criar Múltiplas Postagens

**Postagem sobre Lifestyle:**
```bash
curl -X POST http://localhost:8080/postagens \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOnNlbmhhMTIzNDU2" \
  -d '{
    "titulo": "Dicas para um Home Office Produtivo",
    "texto": "Trabalhar de casa requer disciplina e organização. Aqui estão algumas dicas que me ajudaram a manter a produtividade: criar uma rotina, ter um espaço dedicado, fazer pausas regulares...",
    "tema": {"id": 2},
    "usuario": {"id": 1}
  }'
```

**Postagem sobre Viagens:**
```bash
curl -X POST http://localhost:8080/postagens \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOnNlbmhhMTIzNDU2" \
  -d '{
    "titulo": "Minha Viagem para o Japão",
    "texto": "O Japão é um país fascinante que combina tradição e modernidade de forma única. Durante minha viagem de duas semanas, visitei Tóquio, Quioto e Osaka. Cada cidade ofereceu experiências completamente diferentes...",
    "tema": {"id": 3},
    "usuario": {"id": 1}
  }'
```

### 3.3 Buscar Postagens

**Listar todas as postagens:**
```bash
curl http://localhost:8080/postagens
```

**Buscar postagem específica por ID:**
```bash
curl http://localhost:8080/postagens/1
```

**Buscar postagens por título:**
```bash
curl http://localhost:8080/postagens/titulo/spring
```

## 👥 Cenário 4: Múltiplos Usuários

### 4.1 Cadastrar Segundo Usuário

```bash
curl -X POST http://localhost:8080/usuarios/cadastrar \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Santos",
    "email": "joao.santos@email.com",
    "password": "minhasenha789",
    "photo": "https://avatars.githubusercontent.com/joao"
  }'
```

### 4.2 Login do Segundo Usuário

```bash
curl -X POST http://localhost:8080/usuarios/logar \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao.santos@email.com",
    "password": "minhasenha789"
  }'
```

**Response:**
```json
{
  "id": 2,
  "name": "João Santos",
  "email": "joao.santos@email.com",
  "password": "",
  "photo": "https://avatars.githubusercontent.com/joao",
  "token": "Basic am9hby5zYW50b3NAZW1haWwuY29tOm1pbmhhc2VuaGE3ODk="
}
```

### 4.3 João Cria Uma Postagem

```bash
curl -X POST http://localhost:8080/postagens \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic am9hby5zYW50b3NAZW1haWwuY29tOm1pbmhhc2VuaGE3ODk=" \
  -d '{
    "titulo": "React vs Vue.js: Qual Escolher?",
    "texto": "Ambos são excelentes frameworks JavaScript, mas têm características distintas. React oferece maior flexibilidade e um ecossistema mais maduro, enquanto Vue.js tem uma curva de aprendizado mais suave...",
    "tema": {"id": 1},
    "usuario": {"id": 2}
  }'
```

## 🔄 Cenário 5: Operações de Atualização

### 5.1 Editar Postagem

```bash
curl -X PUT http://localhost:8080/postagens \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOnNlbmhhMTIzNDU2" \
  -d '{
    "id": 1,
    "titulo": "Introdução ao Spring Boot - Atualizado",
    "texto": "Spring Boot é um framework que simplifica o desenvolvimento de aplicações Java. Com suas configurações automáticas e convenções inteligentes, você pode criar APIs REST robustas rapidamente. ATUALIZAÇÃO: Adicionando informações sobre Spring Boot 3.0...",
    "tema": {"id": 1},
    "usuario": {"id": 1}
  }'
```

### 5.2 Atualizar Dados do Usuário

```bash
curl -X PUT http://localhost:8080/usuarios/atualizar \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOnNlbmhhMTIzNDU2" \
  -d '{
    "id": 1,
    "name": "Maria Silva Santos",
    "email": "maria.silva@email.com",
    "password": "novaSenha123456",
    "photo": "https://avatars.githubusercontent.com/maria-nova"
  }'
```

## 🔍 Cenário 6: Consultas Específicas

### 6.1 Ver Perfil Completo do Usuário

```bash
curl http://localhost:8080/usuarios/1
```

**Response com postagens:**
```json
{
  "id": 1,
  "name": "Maria Silva Santos",
  "email": "maria.silva@email.com",
  "password": "$2a$10$...",
  "photo": "https://avatars.githubusercontent.com/maria-nova",
  "postagem": [
    {
      "id": 1,
      "titulo": "Introdução ao Spring Boot - Atualizado",
      "texto": "Spring Boot é um framework...",
      "data": "2024-01-15T14:35:10.123456"
    },
    {
      "id": 2,
      "titulo": "Dicas para um Home Office Produtivo",
      "texto": "Trabalhar de casa requer...",
      "data": "2024-01-15T15:20:30.654321"
    }
  ]
}
```

### 6.2 Ver Tema com Todas as Postagens

```bash
curl http://localhost:8080/temas/1
```

**Response:**
```json
{
  "id": 1,
  "descricao": "Tecnologia",
  "postagens": [
    {
      "id": 1,
      "titulo": "Introdução ao Spring Boot - Atualizado",
      "texto": "Spring Boot é um framework...",
      "data": "2024-01-15T14:35:10.123456",
      "usuario": {
        "id": 1,
        "name": "Maria Silva Santos"
      }
    },
    {
      "id": 4,
      "titulo": "React vs Vue.js: Qual Escolher?",
      "texto": "Ambos são excelentes frameworks...",
      "data": "2024-01-15T16:10:45.789012",
      "usuario": {
        "id": 2,
        "name": "João Santos"
      }
    }
  ]
}
```

### 6.3 Buscar Temas por Descrição

```bash
curl http://localhost:8080/temas/descricao/tech
```

## 🗑️ Cenário 7: Operações de Exclusão

### 7.1 Excluir uma Postagem

```bash
curl -X DELETE http://localhost:8080/postagens/2 \
  -H "Authorization: Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOm5vdmFTZW5oYTEyMzQ1Ng=="
```

**Response:** 204 No Content

### 7.2 Excluir um Tema

> ⚠️ **Atenção:** Excluir um tema também exclui todas as postagens associadas!

```bash
curl -X DELETE http://localhost:8080/temas/3 \
  -H "Authorization: Basic bWFyaWEuc2lsdmFAZW1haWwuY29tOm5vdmFTZW5oYTEyMzQ1Ng=="
```

## 🌐 Cenário 8: Usando JavaScript/Frontend

### 8.1 Exemplo com Fetch API

```javascript
// Cadastrar usuário
async function cadastrarUsuario() {
  const response = await fetch('http://localhost:8080/usuarios/cadastrar', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      name: 'Ana Costa',
      email: 'ana.costa@email.com',
      password: 'senha123456',
      photo: 'https://avatars.githubusercontent.com/ana'
    })
  });
  
  const usuario = await response.json();
  console.log('Usuário cadastrado:', usuario);
  return usuario;
}

// Fazer login
async function fazerLogin(email, password) {
  const response = await fetch('http://localhost:8080/usuarios/logar', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ email, password })
  });
  
  const loginData = await response.json();
  // Salvar token para próximas requisições
  localStorage.setItem('token', loginData.token);
  return loginData;
}

// Criar postagem
async function criarPostagem(titulo, texto, temaId, usuarioId) {
  const token = localStorage.getItem('token');
  
  const response = await fetch('http://localhost:8080/postagens', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': token
    },
    body: JSON.stringify({
      titulo,
      texto,
      tema: { id: temaId },
      usuario: { id: usuarioId }
    })
  });
  
  return await response.json();
}

// Listar postagens
async function listarPostagens() {
  const response = await fetch('http://localhost:8080/postagens');
  return await response.json();
}
```

### 8.2 Exemplo com Axios

```javascript
import axios from 'axios';

// Configurar interceptor para adicionar token automaticamente
axios.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = token;
  }
  return config;
});

const api = axios.create({
  baseURL: 'http://localhost:8080'
});

// Serviços
const usuarioService = {
  cadastrar: (userData) => api.post('/usuarios/cadastrar', userData),
  logar: (credentials) => api.post('/usuarios/logar', credentials),
  atualizar: (userData) => api.put('/usuarios/atualizar', userData),
  buscarPorId: (id) => api.get(`/usuarios/${id}`)
};

const postagemService = {
  listar: () => api.get('/postagens'),
  buscarPorId: (id) => api.get(`/postagens/${id}`),
  buscarPorTitulo: (titulo) => api.get(`/postagens/titulo/${titulo}`),
  criar: (postagem) => api.post('/postagens', postagem),
  atualizar: (postagem) => api.put('/postagens', postagem),
  excluir: (id) => api.delete(`/postagens/${id}`)
};

const temaService = {
  listar: () => api.get('/temas'),
  buscarPorId: (id) => api.get(`/temas/${id}`),
  buscarPorDescricao: (descricao) => api.get(`/temas/descricao/${descricao}`),
  criar: (tema) => api.post('/temas', tema),
  atualizar: (tema) => api.put('/temas', tema),
  excluir: (id) => api.delete(`/temas/${id}`)
};
```

## 🎨 Cenário 9: Exemplo de Aplicação React

### 9.1 Componente de Lista de Postagens

```jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ListaPostagens() {
  const [postagens, setPostagens] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    carregarPostagens();
  }, []);

  const carregarPostagens = async () => {
    try {
      const response = await axios.get('http://localhost:8080/postagens');
      setPostagens(response.data);
    } catch (error) {
      console.error('Erro ao carregar postagens:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div>Carregando...</div>;

  return (
    <div className="lista-postagens">
      <h2>Postagens do Blog</h2>
      {postagens.map(postagem => (
        <div key={postagem.id} className="postagem-card">
          <h3>{postagem.titulo}</h3>
          <p>{postagem.texto}</p>
          <div className="metadados">
            <span>Por: {postagem.usuario.name}</span>
            <span>Tema: {postagem.tema.descricao}</span>
            <span>Data: {new Date(postagem.data).toLocaleDateString()}</span>
          </div>
        </div>
      ))}
    </div>
  );
}

export default ListaPostagens;
```

## 📱 Cenário 10: App Mobile com React Native

```javascript
import AsyncStorage from '@react-native-async-storage/async-storage';

class BlogAPI {
  constructor() {
    this.baseURL = 'http://localhost:8080';
  }

  async request(endpoint, options = {}) {
    const token = await AsyncStorage.getItem('token');
    
    const config = {
      headers: {
        'Content-Type': 'application/json',
        ...(token && { Authorization: token }),
        ...options.headers
      },
      ...options
    };

    const response = await fetch(`${this.baseURL}${endpoint}`, config);
    return response.json();
  }

  // Métodos da API
  login = async (email, password) => {
    const response = await this.request('/usuarios/logar', {
      method: 'POST',
      body: JSON.stringify({ email, password })
    });
    
    if (response.token) {
      await AsyncStorage.setItem('token', response.token);
      await AsyncStorage.setItem('usuario', JSON.stringify(response));
    }
    
    return response;
  };

  getPostagens = () => this.request('/postagens');
  
  createPostagem = (postagem) => this.request('/postagens', {
    method: 'POST',
    body: JSON.stringify(postagem)
  });
}

export default new BlogAPI();
```

## 🧪 Cenário 11: Testes Automatizados

### 11.1 Teste com Jest e Supertest

```javascript
const request = require('supertest');
const app = require('../app');

describe('Blog API Tests', () => {
  let authToken;
  let usuarioId;

  beforeAll(async () => {
    // Cadastrar usuário de teste
    const response = await request(app)
      .post('/usuarios/cadastrar')
      .send({
        name: 'Usuário Teste',
        email: 'teste@email.com',
        password: 'senha123456',
        photo: 'https://teste.com/foto.jpg'
      });
    
    usuarioId = response.body.id;

    // Fazer login
    const loginResponse = await request(app)
      .post('/usuarios/logar')
      .send({
        email: 'teste@email.com',
        password: 'senha123456'
      });
    
    authToken = loginResponse.body.token;
  });

  test('Deve criar uma postagem', async () => {
    const response = await request(app)
      .post('/postagens')
      .set('Authorization', authToken)
      .send({
        titulo: 'Postagem de Teste',
        texto: 'Este é um texto de teste para a postagem.',
        tema: { id: 1 },
        usuario: { id: usuarioId }
      });

    expect(response.status).toBe(201);
    expect(response.body.titulo).toBe('Postagem de Teste');
    expect(response.body.usuario.id).toBe(usuarioId);
  });

  test('Deve listar todas as postagens', async () => {
    const response = await request(app).get('/postagens');
    
    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBe(true);
  });
});
```

## 🔧 Dicas de Troubleshooting

### Problemas Comuns e Soluções

1. **Erro 401 Unauthorized**
   ```bash
   # Verificar se o token está correto
   echo "bWFyaWEuc2lsdmFAZW1haWwuY29tOnNlbmhhMTIzNDU2" | base64 -d
   # Deve retornar: maria.silva@email.com:senha123456
   ```

2. **Erro 400 Bad Request**
   ```json
   // Verificar validações:
   {
     "titulo": "abc", // muito curto (mín. 5 caracteres)
     "texto": "123456789", // muito curto (mín. 10 caracteres)
     "tema": { "id": 999 }, // tema inexistente
     "usuario": { "id": 999 } // usuário inexistente
   }
   ```

3. **CORS Errors no Frontend**
   ```javascript
   // A API já está configurada para aceitar CORS de qualquer origem
   // Se ainda tiver problemas, verifique se está usando HTTP ou HTTPS consistentemente
   ```

## 📊 Monitoramento e Logs

### Verificar Health da API

```bash
# Health check básico
curl http://localhost:8080/actuator/health

# Verificar se todos os endpoints estão respondendo
curl -I http://localhost:8080/usuarios/all
curl -I http://localhost:8080/postagens
curl -I http://localhost:8080/temas
```

---

Estes exemplos cobrem os principais cenários de uso da API Blog Pessoal. Para mais detalhes sobre cada endpoint, consulte a [Documentação da API](API.md) e experimente os endpoints interativamente no [Swagger UI](http://localhost:8080/swagger-ui.html).