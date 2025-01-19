package br.com.pjcode.biblioteca.dao;

import br.com.pjcode.biblioteca.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    @Query("SELECT COUNT(e) FROM Emprestimo e ")
    Long countAllEmprestimos();

    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.dataDevolucaoPrevista = :data AND e.dataDevolucaoReal IS NULL")
    Long countEmprestimosAtrasados(@Param("data") LocalDateTime now);

    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.dataDevolucaoPrevista = :data")
    Long countEmprestimosHoje(@Param("data") LocalDateTime now);


}
