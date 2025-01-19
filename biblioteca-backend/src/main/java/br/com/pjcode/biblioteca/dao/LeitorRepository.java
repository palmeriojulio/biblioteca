package br.com.pjcode.biblioteca.dao;

import br.com.pjcode.biblioteca.domain.Leitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, Long> {

    Boolean existsByCpf(String cpf);

    Leitor findByCpf(String cpf);

    @Query("SELECT COUNT(leitor) FROM Leitor leitor")
    Long countAllLeitores();
}
