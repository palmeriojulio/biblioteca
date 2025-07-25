package br.com.pjcode.biblioteca.dto;

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
public class UsuarioDto {

    private Long id;
    @NotBlank(message = "Usuário não pode estar vazio ou nulo!")
    private String username;
    @NotBlank(message = "Password não pode estar vazio ou nulo!")
    private String password;
    private PerfilDto perfil;

    /**
     * Transformar de DTO para Entity
     * @param dto
     * @return Entity de Usuario
     */
    public static Usuario toUsuario(UsuarioDto dto) {
        return new Usuario(
                dto.getId(),
                dto.getUsername(),
                dto.getPassword(),
                PerfilDto.toPerfil(dto.getPerfil())
        );
    }

    /**
     * Transforma Entity em DTO
     * @param entity
     * @return DTO de Usuario
     */
    public static UsuarioDto fromUsuario(Usuario entity) {
        return new UsuarioDto(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                PerfilDto.fromPerfil(entity.getPerfil())
        );
    }
}
