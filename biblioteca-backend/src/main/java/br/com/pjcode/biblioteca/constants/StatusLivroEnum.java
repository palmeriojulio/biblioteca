package br.com.pjcode.biblioteca.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Palmério Júlio
 * Enum referente ao status atual do livro .
 */
@Getter
@AllArgsConstructor
public enum StatusLivroEnum {
    DISPONIVEL, // O livro está disponível para empréstimo.
    EMPRESTADO, // O livro está atualmente emprestado a um leitor.
    RESERVADO, // O livro foi reservado por um leitor e está aguardando para ser emprestado.
    EM_MANUTENCAO, // O livro está temporariamente fora de circulação devido a manutenção ou reparo.
    PERDIDO, //O livro foi perdido e precisa ser substituído ou encontrado.
    DANIFICADO, //O livro foi danificado e precisa ser reparado ou substituído.
    ARQUIVADO //O livro não está mais disponível para empréstimo e foi movido para o arquivo.
}
