package br.com.pjcode.biblioteca.dao;

import br.com.pjcode.biblioteca.dto.LivrosMaisEmprestadosDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.pjcode.biblioteca.domain.Livro;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByCdu(String cdu);
    Optional<Livro> findByTitulo(String titulo);
    Boolean existsByTitulo(String titulo);
    @Query("SELECT COUNT(l) FROM Livro l")
    Long countAllLivros();
    @Query("SELECT COUNT(e.id) AS quant, l.titulo " +
            "FROM Emprestimo e " +
            "JOIN e.livros l " +
            "GROUP BY l.id, l.titulo")
    List<LivrosMaisEmprestadosDto> livrosMaisEmprestados(Pageable pageable);

}
