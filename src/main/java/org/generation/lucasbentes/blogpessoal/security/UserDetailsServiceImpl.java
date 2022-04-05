package org.generation.lucasbentes.blogpessoal.security;

import java.util.Optional;

import org.generation.lucasbentes.blogpessoal.model.Usuario;
import org.generation.lucasbentes.blogpessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(userName);
		
		// Se nao achar um usuario ele vai expor essa msg de erro
		usuario.orElseThrow(() -> new UsernameNotFoundException(userName + " Este usuario nao foi encontrado"));
		
		// Se a respota do options for verdadeira ele vai retorna um map com os usuarios
		return usuario.map(UserDetailsImpl::new).get();  //os :: seguinifica que e uma atribuicao
		
	}
	
}
