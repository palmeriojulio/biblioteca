package br.com.pjcode.biblioteca.service;

import br.com.pjcode.biblioteca.dao.PerfilRepository;
import br.com.pjcode.biblioteca.dao.UsuarioRepository;
import br.com.pjcode.biblioteca.domain.Perfil;
import br.com.pjcode.biblioteca.domain.Usuario;
import br.com.pjcode.biblioteca.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Usuario register(String username, String password, Perfil perfil) {
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Usuário já existe!");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setPerfil(perfil);

        return usuarioRepository.save(usuario);
    }

    public String authenticate(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Senha inválida!");
        }

        return jwtTokenProvider.generateToken(usuario);
    }
}
