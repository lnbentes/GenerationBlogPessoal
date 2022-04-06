package org.generation.lucasbentes.blogpessoal.controller;

import java.util.List;

import org.generation.lucasbentes.blogpessoal.model.Postagem;
import org.generation.lucasbentes.blogpessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// O controller e responsavel pela comunicacao da nossa api com o cliente(Pstaman, angula...), controla todos os endpoins

@RestController // Da as permicoes para que essa class seja controller
@RequestMapping("/postagens")  // Informa qual url essa class sera acessada
@CrossOrigin(origins = "*", allowedHeaders = "*")  // Faz com que o frontend consiga consumir nossa api. O * informa que a nossa api pode ser acessada de qualquer origin
public class PostagemController {
	
	@Autowired  // Faz com que o spring acesse a interface do controle
	private PostagemRepository repository;
	
	
	//#### Metodos ####
	
	@GetMapping // Informa que se a requisicao do usuario for um get ai vai acionar esse metodo
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(repository.findAll());
		/*O ok e o estatus http. quando o estatus for 200
		se ele for ok, ele vai devolver todas as postagens em forma de lista*/
	}
	
	@GetMapping("/{id}")  //Informa que esse mefoto faz uma busca por id
	public ResponseEntity<Postagem> getById(@PathVariable Long id){ //O path informa que a variavel que vai entra no metodo e uma mariavel do cominho da url(uri)
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}") // Indica que o metodo a baixo faz uma busca por titulo
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
		
	}
	
	@PostMapping //Indica que esse metodo faz uma postagem
	public ResponseEntity<Postagem> post(@RequestBody Postagem postagem){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
		
	}
	
	@PutMapping //Indica que esse metodo faz uma postagem put
	public ResponseEntity<Postagem> put(@RequestBody Postagem postagem){
		
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		repository.deleteById(id);
		
	}

}
