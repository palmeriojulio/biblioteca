package br.com.pjcode.biblioteca.constants;

import lombok.Getter;

/**
 * @author Palmério Júlio
 * Enum referente ao status atual do livro .
 */
@Getter
public enum StatusLivroEnum {
    DISPONIVEL("Disponível"), // O livro está disponível para empréstimo.
    EMPRESTADO("Emprestado"), // O livro está atualmente emprestado a um leitor.
    RESERVADO("Reservado"), // O livro foi reservado por um leitor e está aguardando para ser emprestado.
    EM_MANUTENCAO("Em Manutenção"), // O livro está temporariamente fora de circulação devido a manutenção ou reparo.
    PERDIDO("Perdido"), //O livro foi perdido e precisa ser substituído ou encontrado.
    DANIFICADO("Danificado"), //O livro foi danificado e precisa ser reparado ou substituído.
    ARQUIVADO("Arquivado"); //O livro não está mais disponível para empréstimo e foi movido para o arquivo.

    private String descricao;
    StatusLivroEnum(String descricao) {
        this.descricao= descricao;
    }
}
