package org.generation.lucasbentes.blogpessoal.model;


//Ela nao vai ser uma tabela
public class UsuarioLogin {

	// Nao vai ter marcacoes @ pois so queremos o objeto
	
	private Long id;
	
	private String nome;
	
	private String usuario;
	
	private String senha;
	
	private String foto;
	
	private String token;  // O token que vai ser a chave de ascesso do usuario. a dependencia que gera
	//A partir da senha, ele faz com que o usuario fique no login durante tuda a cessao

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
