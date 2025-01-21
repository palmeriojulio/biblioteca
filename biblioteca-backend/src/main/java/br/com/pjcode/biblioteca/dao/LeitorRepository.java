package br.com.pjcode.biblioteca.dao;

import br.com.pjcode.biblioteca.domain.Leitor;
import br.com.pjcode.biblioteca.dto.LeitorFaixaEtariaDto;
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
    @Query("SELECT new br.com.pjcode.biblioteca.dto.LeitorFaixaEtariaDto(" +
            "SUM(CASE WHEN l.idade BETWEEN 0 AND 12 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN l.idade BETWEEN 13 AND 18 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN l.idade BETWEEN 19 AND 25 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN l.idade BETWEEN 26 AND 40 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN l.idade > 40 THEN 1 ELSE 0 END)) " +
            "FROM Leitor l")
    LeitorFaixaEtariaDto getFaixaEtaria();
}
