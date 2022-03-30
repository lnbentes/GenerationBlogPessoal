package org.generation.lucasbentes.blogpessoal.repository;

import java.util.List;

import org.generation.lucasbentes.blogpessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// E no repository que se faz a comunicacao com o banco de dados as consultas

@Repository  // Informa que e uma class repositorio
public interface PostagemRepository extends JpaRepository<Postagem, Long>{ // Ele tem que ser interface
	
	public List<Postagem> findAllByTituloContainingIgnoreCase(String titulo);
	
}
