package br.com.pjcode.biblioteca.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DashboardDto {
    private Long livros;

    private Long leitores;

    private Long emprestimos;

    private Long entregasHoje;

    private Long entregasEmAtraso;

    private List<LivrosMaisEmprestadosDto> livroMaisEmprestados;

    private LeitorFaixaEtariaDto leitoresFaixaEtaria;
}
