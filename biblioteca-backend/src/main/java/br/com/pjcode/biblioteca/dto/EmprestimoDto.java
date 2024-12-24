package br.com.pjcode.biblioteca.dto;

import br.com.pjcode.biblioteca.constants.StatusEmprestimoEnum;
import br.com.pjcode.biblioteca.domain.Emprestimo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmprestimoDto {

    private Long id;
    private LocalDateTime dataDoEmprestimo;
    private LocalDateTime dataDevolucaoPrevista;
    private LocalDateTime dataDevolucaoReal;
    private String status;
    private List<LivroDto> livros;
    private LeitorDto leitor;


    public static Emprestimo toEmprestimo(EmprestimoDto dto) {
        return new Emprestimo(
                dto.getId(),
                dto.getDataDoEmprestimo(),
                dto.getDataDevolucaoPrevista(),
                dto.getDataDevolucaoReal(),
                dto.getStatus(),
                LivroDto.toConvertList(dto.getLivros()),
                LeitorDto.toLeitor(dto.getLeitor())
        );
    }

    public static EmprestimoDto fromEmprestimo(Emprestimo entity) {
        return new EmprestimoDto(
                entity.getId(),
                entity.getDataDoEmprestimo(),
                entity.getDataDevolucaoPrevista(),
                entity.getDataDevolucaoReal(),
                entity.getStatus(),
                LivroDto.fromConvertList(entity.getLivros()),
                LeitorDto.fromLeitor(entity.getLeitor())
        );
    }
}
