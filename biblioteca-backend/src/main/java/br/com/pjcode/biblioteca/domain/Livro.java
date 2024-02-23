package br.com.pjcode.biblioteca.domain;

import br.com.pjcode.biblioteca.constants.StatusLivroEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "livro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Livro implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "livro_id_seq", sequenceName = "livro_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "livro_id_seq")
	@Column(name = "id_livro")
	private Long id;

	@Column(name = "cdu", length = 10)
	private String cdu;

	@Column(name = "titulo",length = 80)
	private String titulo;

	@Column(name = "autor", length = 50)
	private String autor;

	@Column(name = "editora", length = 50)
	private String editora;

	@Column(name = "status", length = 20)
	@Enumerated(EnumType.STRING)
	private StatusLivroEnum status;

}
