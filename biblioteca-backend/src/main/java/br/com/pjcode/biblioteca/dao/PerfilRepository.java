package br.com.pjcode.biblioteca.dao;

import br.com.pjcode.biblioteca.constants.RoleEnum;
import br.com.pjcode.biblioteca.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByName(RoleEnum name);
}
