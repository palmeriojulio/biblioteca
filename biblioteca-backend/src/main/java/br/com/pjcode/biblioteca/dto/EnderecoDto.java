package br.com.pjcode.biblioteca.dto;

import br.com.pjcode.biblioteca.domain.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Palmério Júlio
 * Classe que usa o padrão "Data Transfer Object" para transferir dados entre diferentes camadas da aplicação.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EnderecoDto {

    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;

    /**
     * Transformar de DTO para Entity
     * @param enderecoDto
     * @return Entity de Endereco
     */
    public static Endereco toEndereco(EnderecoDto enderecoDto) {
        return new Endereco(
                enderecoDto.getId(),
                enderecoDto.getLogradouro(),
                enderecoDto.getNumero(),
                enderecoDto.getBairro(),
                enderecoDto.getCidade(),
                enderecoDto.getUf()
        );
    }

    /**
     * Transformar Entity em DTO
     * @param endereco
     * @return Entity de Endereco
     */
    public static EnderecoDto fromEndereco(Endereco endereco) {
        return new EnderecoDto(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf()
        );
    }
}
