package br.com.pjcode.biblioteca.domain;

import java.io.Serial;
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
@Table(name = "endereco")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Endereco implements Serializable{
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "endereco_id_seq", sequenceName = "endereco_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_id_seq")
	@Column(name = "id_endereco")
	private Long id;
	
	@Column(name = "logradouro", length = 80)
	private String logradouro;
	
	@Column(name = "numero", length = 5)
	private String numero;
	
	@Column(name = "bairro", length = 80)
	private String bairro;
	
	@Column(name = "cidade", length = 80)
	private String cidade;
	
	@Column(name = "uf", length = 50)
	private String uf;
}
