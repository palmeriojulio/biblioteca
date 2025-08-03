package br.com.pjcode.biblioteca.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pjcode.biblioteca.domain.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	
}
