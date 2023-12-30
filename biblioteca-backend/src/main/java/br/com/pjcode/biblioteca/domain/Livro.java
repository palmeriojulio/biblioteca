package br.com.pjcode.biblioteca.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Livro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "livro_id_seq", sequenceName = "livro_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "livro_id_seq")
	@Column(name = "id_livro")
	private Long id;
	
	@Column(name = "cdu", length = 4)
	private String cdu;
	
	@Column(name = "nome",length = 80)
	private String nome;
	
	@Column(name = "autor", length = 50)
	private String autor;
	
	@Column(name = "editora", length = 50)
	private String editora;

}
