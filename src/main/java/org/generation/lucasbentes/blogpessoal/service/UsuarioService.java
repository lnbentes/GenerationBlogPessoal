package org.generation.lucasbentes.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.lucasbentes.blogpessoal.model.Usuario;
import org.generation.lucasbentes.blogpessoal.model.UsuarioLogin;
import org.generation.lucasbentes.blogpessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public Optional<Usuario> cadastraUsuario(Usuario usuario){
		// Verificar se a um usuario no db
		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()){
			return Optional.empty();  // Se ouver retona vazio			
		}
		
		// Se nao ouver um usuario vou pegar os dados do novo e crip. a senha e salvar no banco
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		
		// Depois de criptogravar a senha ele vai salvar o usurio novo
		return Optional.of(usuarioRepository.save(usuario));
	
	}
	
	
	
	// Metodo de loging
	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){
		// Verificando se a variavel usuarioLogin existe e armazenando a resposta na variavel usuario
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
				
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(geradorBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(usuario.get().getSenha());
				
				// Depois que o usuarioLogin foi verificado e recebeu todos os dados vai retorna ele com o novo token de acesso
				return usuarioLogin;
			}
		}
		
		return Optional.empty();
		
	}
	
	// Mudar a senha do usuario
	public Optional<Usuario> atualizarUsuario (Usuario usuario) {   
		
		// Verificar se ele esta no sistema
		if (usuarioRepository.findById(usuario.getId()).isPresent()) {     
			
			//Chamar o user que esta no banco
			Optional<Usuario> cadeUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());
			
			// Verificar se as senha sao iguais
			if ((cadeUsuario.isPresent()) && (cadeUsuario.get().getId() != usuario.getId())) {
				throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Já existente",null);
			}
			 
			// Se for diferente atualizar para a nova
			usuario.setSenha(criptografarSenha(usuario.getSenha()));      
			
			// Salvando as modificacoes
			return Optional.of(usuarioRepository.save(usuario));     
			}
		
		return Optional.empty(); 
		}
	
	
	
	
	//######## funcoes #######
	
	private String criptografarSenha(String senha){
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  // Essa objeto vai ser responsavel pela cryp.
		
		return encoder.encode(senha);
	}

	
	private boolean compararSenhas(String senhaEntrada, String senhaBanco){
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(senhaEntrada, senhaBanco);
	}
	
	
	private String geradorBasicToken(String usuario, String senha){
		
		String token = usuario + ":" + senha;
		
		// Pegar o token e converte para binario usando o protocolo us-ascii que informa que sao as letra de teclado
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		
		return "Basic " + new String(tokenBase64);
	}
	
	
}
