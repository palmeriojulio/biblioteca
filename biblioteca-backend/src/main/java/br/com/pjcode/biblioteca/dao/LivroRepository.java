package br.com.pjcode.biblioteca.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pjcode.biblioteca.domain.Livro;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByCdu(String cdu);
    Optional<Livro> findByTitulo(String titulo);
    Boolean existsByTitulo(String titulo);
}
