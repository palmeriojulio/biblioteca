package br.com.pjcode.biblioteca.dto;

import java.time.LocalDate;

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
	private LocalDate dataNascimento;
	@Size(min = 10, max = 11, message = "Telefone deve ter entre 10 e 11 caracteres")
	private String telefone;
	private String profissao;
	private String escola;
	private String serie;
	private String curso;
	private String turno;
	@NotBlank(message = "endereço não pode estar em branco e não pode ser nulo.")
	private EnderecoDto endereco;

	/**
	 * Transformar de DTO para Entity
	 * @param leitorDto
	 * @return Entity de Leitor
	 */
	public static Leitor toLeitor(LeitorDto leitorDto) {
		return new Leitor(
				leitorDto.getId(),
				leitorDto.getNome(),
				leitorDto.getCpf(),
				leitorDto.getRg(),
				leitorDto.getDataNascimento(),
				leitorDto.getTelefone(),
				leitorDto.getProfissao(),
				leitorDto.getEscola(),
				leitorDto.getSerie(),
				leitorDto.getCurso(),
				leitorDto.getTurno(),
				EnderecoDto.toEndereco(leitorDto.getEndereco())
				);
	}

	/**
	 * Transformar Entity em DTO
	 * @param
	 * @return DTO de Leitor
	 */
	public static LeitorDto fromLeitor(Leitor leitor) {
		return new LeitorDto(
				leitor.getId(),
				leitor.getNome(),
				leitor.getCpf(),
				leitor.getRg(),
				leitor.getDataNascimento(),
				leitor.getTelefone(),
				leitor.getProfissao(),
				leitor.getEscola(),
				leitor.getSerie(),
				leitor.getCurso(),
				leitor.getTurno(),
				EnderecoDto.fromEndereco(leitor.getEndereco())
				);

	}
}