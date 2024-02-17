package br.com.pjcode.biblioteca.dao;

import br.com.pjcode.biblioteca.domain.Leitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, Long> {

    Boolean existsByCpf(String cpf);

    Leitor findByCpf(String cpf);
}
