package br.com.pjcode.biblioteca.dao;

import br.com.pjcode.biblioteca.domain.Leitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeitorRepository extends JpaRepository<Leitor, Long> {

    Boolean existsByCpf(String cpf);
}
