package br.com.pjcode.biblioteca.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pjcode.biblioteca.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	Optional<Usuario> findByUsername(String username);
	
}
