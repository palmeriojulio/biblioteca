package br.com.pjcode.biblioteca.dao;

import br.com.pjcode.biblioteca.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    @Query("SELECT COUNT(e) FROM Emprestimo e ")
    Long countAllEmprestimos();

    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.status = 'Atrasado' AND e.dataDevolucaoPrevista < :dataAtual")
    Long countEmprestimosAtrasados(@Param("dataAtual") LocalDateTime now);

    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE DATE(e.dataDevolucaoPrevista) = :dataAtual")
    Long countEmprestimosHoje(@Param("dataAtual")LocalDate now);

    @Query("SELECT e FROM Emprestimo e WHERE e.dataDevolucaoPrevista " +
            "< :dataAtual AND e.dataDevolucaoReal IS NULL AND e.status != 'Atrasado'")
    List<Emprestimo> findEmprestimosAtrasados(LocalDateTime dataAtual);
}
