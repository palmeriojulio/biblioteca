package br.com.pjcode.biblioteca.dto;

import br.com.pjcode.biblioteca.domain.Endereco;
import br.com.pjcode.biblioteca.domain.Leitor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Palmério Júlio
 * Classe que usa o padrão "Data Transfer Object" para transferir dados entre diferentes camadas da aplicação.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LeitorDto {

    private Long id;
    @NotBlank(message = "Nome não pode estar em branco e não pode ser nulo.")
    private String nome;
    @NotBlank(message = "CPF não pode estar em branco e não pode ser nulo.")
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;
    private String telefone;
    private String profissao;
    private String escola;
    private String serie;
    private String curso;
    private String turno;
    @NotBlank(message = "endereço não pode estar em branco e não pode ser nulo.")
    private Endereco endereco;


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
                //EnderecoDto.toEndereco(leitorDto.getPessoa()),
                leitorDto.getEndereco()
        );
    }

}