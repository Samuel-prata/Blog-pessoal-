package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	public void start() {
		usuarioRepository.deleteAll();

		usuarioRepository.save(new Usuario(0l, "Samuel Silverio", "samuel@email.pf", "21082001", null));
		usuarioRepository.save(new Usuario(0l, "Suzi Silverio", "suzi@email.pf", "13101973", null));
		usuarioRepository.save(new Usuario(0l, "Kaiane Micaele", "kaiane@email.pf", "10061003", null));
		usuarioRepository.save(new Usuario(0l, "Julia Silverio", "julia@email.pf", "10092005", null));
	}

	@Test
	@DisplayName("Retornar 1 usuário")
	public void RetornarUsuario() {

		var usuario = usuarioRepository.findByUser("samuel@email.pf");

		assertTrue(usuario.get().getUser().equals("samuel@email.pf"));

	}

	@Test
	@DisplayName("Retornar 3 usuários")
	public void retornarTres() {
		var lista = usuarioRepository.findAllByNameContainingIgnoreCase("Silverio");

		assertEquals(3, lista.size());
		assertTrue(lista.get(0).getName().equals("Samuel Silverio"));
		assertTrue(lista.get(1).getName().equals("Suzi Silverio"));
		assertTrue(lista.get(2).getName().equals("Julia Silverio"));

	}
	
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
}
