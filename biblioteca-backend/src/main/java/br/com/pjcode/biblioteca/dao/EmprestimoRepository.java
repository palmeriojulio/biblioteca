package br.com.pjcode.biblioteca.dao;

import br.com.pjcode.biblioteca.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    /**
     * Encontra um empréstimo pelo prazo de devolução.
     * @param dataDevolucaoPrevista data de devolução prevista
     * @return um empréstimo encontrado pelo prazo de devoluçao, ou um optional vazio se n o houver nenhum.
     */
    Optional<Emprestimo> findByDataDevolucaoPrevista(LocalDateTime dataDevolucaoPrevista);

    /**
     * Encontra todos os empréstimos que não foram devolvidos até a data informada.
     * @param data data atual
     * @return lista de empréstimo atrasados
     */
    @Query("SELECT e FROM Emprestimo e WHERE e.dataDevolucaoPrevista = :data AND e.dataDevolucaoReal IS NULL")
    List<Emprestimo> findAtrasados(@Param("data") LocalDateTime now);
}
