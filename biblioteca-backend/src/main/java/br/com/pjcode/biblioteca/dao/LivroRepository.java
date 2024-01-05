package br.com.pjcode.biblioteca.dao;

import br.com.pjcode.biblioteca.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Livro findByCdu(String cdu);
    Livro findByNome(String nome);
    Boolean existsByNome(String nome);
}
