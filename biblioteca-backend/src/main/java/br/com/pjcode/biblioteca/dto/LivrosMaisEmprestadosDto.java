package br.com.pjcode.biblioteca.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivrosMaisEmprestadosDto {

    private Long quant;
    private String titulo;

    // Construtor necessário para a consulta JPQL
    public LivrosMaisEmprestadosDto(Long quant, String titulo) {
        this.quant = quant;
        this.titulo = titulo;
    }
}
