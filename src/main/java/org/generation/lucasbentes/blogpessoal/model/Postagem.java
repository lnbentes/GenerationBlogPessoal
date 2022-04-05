package org.generation.lucasbentes.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*A pasta model fica as Tabelas do nosso banco de dados*/

// As anotacoes. (Tem que importar as anotacoes)
@Entity  // Nesse caso o Entity vai indicar que essa class e uma tabela
@Table(name = "tb_postagem")  // Nos parenteses coloca o nome da tabela

public class Postagem {
	
	// Atributos da tabela
	
	@Id  // Indica que e a chave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //Vai fazer o autoencremento do abributo id
	private Long id;

	@NotNull  // Informa que nao aceita informacao nula
	private String titulo;
	
	@NotNull
	@Size(min = 4, max = 140)  // Define um tamanho minimo e maximo de caracteres pro texto
	private String texto;
	
	@UpdateTimestamp  // Vai marcar a postagem com a data e a hora
	private LocalDateTime date;
	
	@ManyToOne  // Ele faz a comunicacao com a chave estrangeira. Informa que esse e de muitos para um
	@JsonIgnoreProperties("postagem")  //Faz com que pare de apresentar infromacoes json apartir de um determinado atributo
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

	
	
	
	// Os metodos, get's set's
	
	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
