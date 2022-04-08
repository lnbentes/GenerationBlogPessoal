package org.generation.lucasbentes.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.generation.lucasbentes.blogpessoal.model.Usuario;
import org.generation.lucasbentes.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private UsuarioService usuarioServece;
	
	// O TestRest vai fazer os test http, put, get... E ele e so usado no testController
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar apenas um usuario")
	public void cadastraUmUsuario() {
		
		// O httpEntity vai fazer o corpo da requisicao
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, "lucas bentes", "lucas@bentes.com", "12345", "https://i.imgur.com/FETvs2O.jpg"));
		
		// vai fazer o post, put...                                  -O caminho do controller|Avisar que e post|O que que postar|A class de referencia
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuario/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		// Fazer o test
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		//se o codigo de criar = ao codigo que a resposta vai gerar
		//sempre e o que quero, com a resposta que vem
		
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		//Saber se o nome da requisicao = ao nome cadastrado
		
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
		
	}
	
	
	@Test
	@Order(2)
	@DisplayName("Nao deve permitir duplicar usuario")
	public void naoDuplicarUsuario() {
		
		// INcluindo um usuario na memorio
		usuarioServece.cadastraUsuario(new Usuario(0L, "joao toso", "joao@toso.com", "67890", "https://i.imgur.com/FETvs2O.jpg"));
		
		//Tentando cadastar o mesmo usuario
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, "joao toso", "joao@toso.com", "67890", "https://i.imgur.com/FETvs2O.jpg"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuario/cadatrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		
	}
	
}
