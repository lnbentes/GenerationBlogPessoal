package org.generation.lucasbentes.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.lucasbentes.blogpessoal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	// O Optional serva para quando nao sabermos a resposta que pode vim. No caso se existe ou nao usuario
	// Vai procurar por um nome e devolver uma lista se ouver
	public Optional<Usuario> findByUsuario(String usuario);
	
	public List<Usuario> findAllByNomeContainingIgnoreCase(String nome);
	
}
