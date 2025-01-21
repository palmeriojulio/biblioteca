package br.com.pjcode.biblioteca.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.br.CPF;

import br.com.pjcode.biblioteca.domain.Leitor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Palmério Júlio
 * Classe que usa o padrão "Data Transfer Object" para transferir dados entre diferentes camadas da aplicação.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeitorDto {

	private Long id;
	@NotBlank(message = "Nome do leitor não pode estar em vazio ou nulo!")
	private String nome;
	@CPF(message = "CPF inválido ou vazio!")
	private String cpf;
	private String rg;
	@PastOrPresent(message = "Campo data nascimento inválido!")
	private LocalDateTime dataNascimento;
	private Integer idade;
	@Size(min = 10, max = 11, message = "Telefone deve ter entre 10 e 11 caracteres")
	private String telefone;
	private String profissao;
	private String escola;
	private String serie;
	private String curso;
	private String turno;
	private Boolean ativo;
	private EnderecoDto endereco;

	/**
	 * Transformar de DTO para Entity
	 * @param dto
	 * @return Entity de Leitor
	 */
	public static Leitor toLeitor(LeitorDto dto) {
		return new Leitor(
				dto.getId(),
				dto.getNome(),
				dto.getCpf(),
				dto.getRg(),
				dto.getDataNascimento(),
				dto.getIdade(),
				dto.getTelefone(),
				dto.getProfissao(),
				dto.getEscola(),
				dto.getSerie(),
				dto.getCurso(),
				dto.getTurno(),
				dto.getAtivo(),
				EnderecoDto.toEndereco(dto.getEndereco())
		);
	}

	/**
	 * Transformar Entity em DTO
	 * @param entity
	 * @return DTO de Leitor
	 */
	public static LeitorDto fromLeitor(Leitor entity) {
		return new LeitorDto(
				entity.getId(),
				entity.getNome(),
				entity.getCpf(),
				entity.getRg(),
				entity.getDataNascimento(),
				entity.getIdade(),
				entity.getTelefone(),
				entity.getProfissao(),
				entity.getEscola(),
				entity.getSerie(),
				entity.getCurso(),
				entity.getTurno(),
				entity.getAtivo(),
				EnderecoDto.fromEndereco(entity.getEndereco())
		);
	}
}