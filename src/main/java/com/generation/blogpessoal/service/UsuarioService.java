package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUser(usuario.getUser()).isPresent()) {
			return Optional.empty();
		}

		usuario.setPassword(criptografarSenha(usuario.getPassword()));

		return Optional.of(usuarioRepository.save(usuario));
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		if (usuarioRepository.findById(usuario.getId()).isPresent()) {

			Optional<Usuario> buscaUsuario = usuarioRepository.findByUser(usuario.getUser());

			if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário ja existe", null);

			}
			usuario.setPassword(criptografarSenha(usuario.getPassword()));
			return Optional.ofNullable(usuarioRepository.save(usuario));
		}

		return Optional.empty();

	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
		Optional<Usuario> usuario = usuarioRepository.findByUser(usuarioLogin.get().getUser());

		if (usuario.isPresent()) {

			if (compararSenha(usuarioLogin.get().getPassword(), usuario.get().getPassword())) {

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setName(usuario.get().getName());
				usuarioLogin.get().setPhoto(usuario.get().getPhoto());
				usuarioLogin.get().setToken(gerarTokenBasic(usuarioLogin.get().getUser(), usuarioLogin.get().getPassword()));
				usuarioLogin.get().setPassword(usuario.get().getPassword());

				return usuarioLogin;
			}
		}
		return Optional.empty();
	}

	private String gerarTokenBasic(String user, String password) {
		String token = user + ":" + password;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        
        return "Basic " + new String(tokenBase64);
		
	}

	private boolean compararSenha(String password, String password2) {
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		
		return encode.matches(password, password2);
	}

	private String criptografarSenha(String password) {
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		
		return encode.encode(password);
	}
}
