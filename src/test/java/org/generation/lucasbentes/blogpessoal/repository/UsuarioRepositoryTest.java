package org.generation.lucasbentes.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.lucasbentes.blogpessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)  // Indicar que e uma class test e usa uma porta randomica
@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // Defini o tipo de teste que vai ser
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// Agora comeca os tests. Eles tem que ter inicio, meio e fim
	
	@BeforeAll  // O que vai fazer no inicio, normalmente criar os usuario
	void start() {
		
		// Salvar o novo usuario na memoria, obs: no id usamo 0L para incrementar
		usuarioRepository.save(new Usuario(0L, "lucas bentes", "lucas@bentes.com", "12345", "https://i.imgur.com/FETvs2O.jpg"));
		usuarioRepository.save(new Usuario(0L, "joao toso", "joao@toso.com", "67890", "https://i.imgur.com/FETvs2O.jpg"));
		usuarioRepository.save(new Usuario(0L, "lucas felipe", "felipe@mendes.com", "123678", "https://i.imgur.com/FETvs2O.jpg"));
	
	}
	
	@Test  // O teste em si
	@DisplayName("Retorna apenas um usuario")  // Mgs que vai mostra quando rodar o test
	public void deveRetornarUmUsuario() {
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@toso.com");  // Verificar se o usuario esta no banco e retorne ele
		
		assertTrue(usuario.get().getUsuario().equals("joao@toso.com"));  // Saber se o email do banco e realmente igual e retornar um ok
				
	}
	
	@Test
	@DisplayName("Retorna dois usuario")
	public void deveRetornarDoisUsuario() {
		
		List<Usuario> listaUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("lucas");  // verifica e retorna uma lista de usuario
		
		assertEquals(2, listaUsuarios.size());
		
//		for(int i = 0; i < listaUsuarios.size(); i++) {
//			assertTrue(listaUsuarios.get(i).getNome().equals("lucas"));
//		}
		
		assertTrue(listaUsuarios.get(0).getNome().equals("lucas bentes"));
		assertTrue(listaUsuarios.get(1).getNome().equals("lucas felipe"));	
	}
	
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();  // Vai garantir a limpeza da memoria do banco
	}

	
}
