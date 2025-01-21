package br.com.pjcode.biblioteca.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "leitor")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Leitor implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "leitor_id_seq", sequenceName = "leitor_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leitor_id_seq")
	@Column(name = "id_leitor")
	private Long id;

	@Column(name = "nome", length = 80)
	private String nome;

	@Column(name = "cpf", length = 11)
	private String cpf;

	@Column(name = "rg", length = 20)
	private String rg;

	@Column(name = "data_nascimento")
	private LocalDateTime dataNascimento;

	@Column(name = "idade")
	private Integer idade;

	@Column(name = "telefone", length = 15)
	private String telefone;

	@Column(name = "profissao", length = 50)
	private String profissao;

	@Column(name = "escola", length = 50)
	private String escola;

	@Column(name = "serie", length = 10)
	private String serie;

	@Column(name = "curso", length = 25)
	private String curso;

	@Column(name = "turno", length = 15)
	private String turno;

	@Column(name = "ativo")
	private Boolean ativo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
	private Endereco endereco;
}
