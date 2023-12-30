package br.com.pjcode.biblioteca.dto;

import br.com.pjcode.biblioteca.domain.Livro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LivroDto {

    private Long id;
    private String cdu;
    private String nome;
    private String autor;
    private String editora;

    public static Livro toLivro(LivroDto dto) {
        return new Livro(
                dto.getId(),
                dto.getCdu(),
                dto.getNome(),
                dto.getAutor(),
                dto.getEditora()
        );
    }

    public static LivroDto fromLivro(Livro entity){
        return new LivroDto(
                entity.getId(),
                entity.getCdu(),
                entity.getNome(),
                entity.getAutor(),
                entity.getEditora()
        );
    }

}
