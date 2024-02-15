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
     * @param dto
     * @return Entity de Endereco
     */
    public static Endereco toEndereco(EnderecoDto dto) {
        return new Endereco(
                dto.getId(),
                dto.getLogradouro(),
                dto.getNumero(),
                dto.getBairro(),
                dto.getCidade(),
                dto.getUf()
        );
    }

    /**
     * Transformar Entity em DTO
     * @param entity
     * @return Entity de Endereco
     */
    public static EnderecoDto fromEndereco(Endereco entity) {
        return new EnderecoDto(
                entity.getId(),
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getUf()
        );
    }
}
