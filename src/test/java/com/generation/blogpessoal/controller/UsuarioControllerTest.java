package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

import jakarta.persistence.Basic;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // CONTEUDO DENTRO DOS PARANTESES INDICA QUE OUTRA PORTA
																// SERÁ
@TestInstance(Lifecycle.PER_CLASS) // NESSA LINDO É INDICADO QUE O CICLO DE TESTES SERÁ FEITO POR CLASSES
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRest;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	public void start() {
		usuarioRepository.deleteAll();

		usuarioService.cadastrarUsuario(new Usuario(0l, "samuel", "samuel@email.pf", "21082001", null));
		// CHAMADA DO METODO CADASTRAR NA CLASSE SERVICE DE USUARIO, SEGUIDO DA ISERÇÃO
		// DOS DADOS NO CONSTRUTOR DA CLASSE MODEL USUARIO
	}

	@Test
	@DisplayName("Cadastrar um usuário")
	public void criar() {

		var requisicao = new HttpEntity<Usuario>(new Usuario(0l, "Kaiane", "kaiane@email.com", "12345678", null));
		// REQUISIÇÃO FEITA COM HTTPENTITY INSERINDO OS DADOS NO CONSTRUTOR DA CLASSE
		// MODEL USUARIO

		var resposta = testRest.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		// A RESPOSTA É ENVIADA ATRAVÉS DO METODO EXCHANGE QUE PRECISA DE 4 PARAMETROS:
		// A URL = /USUARIO/CADASTRAR | O METODO = NO EXEMPLO FOI UTILIZADO O POST
		// O OBJETO DA REQUISIÇÃO QUE SERÁ A VARIAVEL REQUISICAO | O CONTEÚDO QUE SERÁ
		// ESPERADO NO CORPO DA RESPOSTA

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getName(), resposta.getBody().getName());
		assertEquals(requisicao.getBody().getUser(), resposta.getBody().getUser());
	}

	@Test
	@DisplayName("não deve multiplicar um usuário")
	public void naoDuplicar() {

		usuarioService.cadastrarUsuario(new Usuario(0l, "Suzi silverio", "suzi@email.com", "12345678", null));
		// UTILZIANDO O METODO DA SERVICE

		var requisicao = new HttpEntity<Usuario>(new Usuario(0l, "Suzi silverio", "suzi@email.com", "12345678", null));
		// REQUISIÇÃO COM ENTITY

		var resposta = testRest.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		// RESPOSTA COM EXCHANGE

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Deve atualizar um usuário")
	public void atualizarUsuario() {

		var cadastrar = usuarioService.cadastrarUsuario(new Usuario(0l, "jovem", "jovem@email.com", "jovem123", null));
		Usuario atualizar = new Usuario(cadastrar.get().getId(), "jovem novo", "jovem22@email.com", "jovemmacarena",
				null);

		var requisicao = new HttpEntity<Usuario>(atualizar);

		var resposta = testRest.withBasicAuth("samuel@email.pf","21082001" ).exchange("/usuarios/atualizar",
				HttpMethod.PUT, requisicao, Usuario.class);
		//USANDO WITHBASICAUTH PARA AUTORIZAR ACESSO AO METODO DE ATUALIZAÇÃO

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getName(), resposta.getBody().getName());
		assertEquals(requisicao.getBody().getUser(), resposta.getBody().getUser());
	}

	@Test
	@DisplayName("Deve listar todos")
	public void listarTodosTeste() {
		
		usuarioService.cadastrarUsuario(new Usuario(0l, "Naruto", "naruto@gmail.com", "konoha1234", null));
		usuarioService.cadastrarUsuario(new Usuario(0l, "Sasuke", "sasuke@gmail.com", "konoha4321", null));
		
		var resposta = testRest.withBasicAuth("samuel@email.pf", "21082001").exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		//PRIMEIRO PARAMETRO PARA IDENTIFICAR SE A RESPOSTA ÉESPERADA E O SEGUNDO PARA CHECAR O TIPO DE RESPOSTA
	}
	
	@AfterAll
	public void encerrar() {
		usuarioRepository.deleteAll();
	}
}
