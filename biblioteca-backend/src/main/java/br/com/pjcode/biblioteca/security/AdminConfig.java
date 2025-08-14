package br.com.pjcode.biblioteca.security;

import br.com.pjcode.biblioteca.dao.PerfilRepository;
import br.com.pjcode.biblioteca.dao.UsuarioRepository;
import br.com.pjcode.biblioteca.domain.Perfil;
import br.com.pjcode.biblioteca.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class AdminConfig implements CommandLineRunner {

    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var perfilAdmin = perfilRepository.findByNome(Perfil.Values.ADMIN.name());
        var usuarioAdmin = usuarioRepository.findByUsername("admin");

        usuarioAdmin.ifPresentOrElse(
                usuario -> {
                     System.out.println("Usuário admin já existe.");
                },
                () -> {
                    var novoUsuario = new Usuario();
                    novoUsuario.setUsername("admin");
                    novoUsuario.setPassword(passwordEncoder.encode("admin"));
                    novoUsuario.setPerfil(Set.of((Perfil) perfilAdmin));
                    usuarioRepository.save(novoUsuario);
                }
        );
    }
}
