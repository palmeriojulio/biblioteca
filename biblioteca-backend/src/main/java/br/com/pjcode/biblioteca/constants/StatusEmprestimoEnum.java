package br.com.pjcode.biblioteca.constants;

import lombok.Getter;

/**
 * @author Palmério Júlio
 * Enum referente ao status do emprestimo.
 */
public enum StatusEmprestimoEnum {
    PENDENTE("Pendente"), // O empréstimo foi solicitado, mas ainda não foi concluído.
    ATIVO("Ativo"), // O empréstimo está em andamento e os livros foram emprestados para o leitor.
    CONCLUIDO("Concluido"), // O empréstimo foi devolvido e todos os livros foram devolvidos com sucesso.
    ATRASADO("Atrasado"), // O empréstimo não foi devolvido dentro do prazo estabelecido.
    CANCELADO("Cancelado"); // O empréstimo foi cancelado antes da conclusão.

    @Getter
    private String statusEmprestimo;

    StatusEmprestimoEnum(String statusEmprestimo) {
        this.statusEmprestimo = statusEmprestimo;
    }
}
