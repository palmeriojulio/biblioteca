package br.com.pjcode.biblioteca.dto;

import br.com.pjcode.biblioteca.constants.RoleEnum;
import br.com.pjcode.biblioteca.domain.Perfil;
import br.com.pjcode.biblioteca.domain.Usuario;
import jakarta.validation.constraints.NotBlank;
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
public class PerfilDto {

    private Long id;
    @NotBlank(message = "Perfil não pode estar vazio ou nulo!")
    private RoleEnum perfil;

    /**
     * Transformar de DTO para Entity
     * @param dto
     * @return Entity de Perfil
     */
    public static Perfil toPerfil(PerfilDto dto) {
        return new Perfil(
                dto.getId(),
                dto.getPerfil()
        );
    }

    /**
     * Transforma Entity em DTO
     * @param entity
     * @return DTO de Perfil
     */
    public static PerfilDto fromPerfil(Perfil entity) {
        return new PerfilDto(
                entity.getId(),
                entity.getPerfil()
        );
    }
}

