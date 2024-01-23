package br.com.pjcode.biblioteca.dto;

import br.com.pjcode.biblioteca.domain.Livro;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LivroDto {

    private Long id;
    @NotBlank(message = "CDU não pode estar em branco e não pode ser nulo.")
    private String cdu;
    @NotBlank(message = "Título não pode estar em branco e não pode ser nulo.")
    private String titulo;
    @NotBlank(message = "Autor não pode estar em branco e não pode ser nulo.")
    private String autor;
    @NotBlank(message = "Editora não pode estar em branco e não pode ser nulo.")
    private String editora;

    /**
     * @param dto
     * @return entity Livro
     */
    public static Livro toLivro(LivroDto dto) {
        return new Livro(
                dto.getId(),
                dto.getCdu(),
                dto.getTitulo(),
                dto.getAutor(),
                dto.getEditora()
        );
    }

    /**
     *
     * @param entity
     * @return dto
     */
    public static LivroDto fromLivro(Livro entity){
        return new LivroDto(
                entity.getId(),
                entity.getCdu(),
                entity.getTitulo(),
                entity.getAutor(),
                entity.getEditora()
        );
    }

}
