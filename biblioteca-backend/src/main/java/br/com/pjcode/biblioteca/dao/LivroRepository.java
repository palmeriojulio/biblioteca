package br.com.pjcode.biblioteca.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pjcode.biblioteca.domain.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Livro findByCdu(String cdu);
    Livro findByTitulo(String titulo);
    Boolean existsByTitulo(String titulo);
}
