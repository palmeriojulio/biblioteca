package br.com.pjcode.biblioteca.resource;

import br.com.pjcode.biblioteca.domain.Perfil;
import br.com.pjcode.biblioteca.domain.Usuario;
import br.com.pjcode.biblioteca.dto.AuthResponseDto;
import br.com.pjcode.biblioteca.dto.LoginDto;
import br.com.pjcode.biblioteca.dto.PerfilDto;
import br.com.pjcode.biblioteca.dto.UsuarioDto;
import br.com.pjcode.biblioteca.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/biblioteca/auth")
public class AuthResource {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDto usuarioDto) {
        Perfil perfil = PerfilDto.toPerfil(usuarioDto.getPerfil());
        Usuario usuario = authService.register(usuarioDto.getUsername(), usuarioDto.getPassword(), perfil);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String token = authService.authenticate(loginDto.getUsername(), loginDto.getPassword());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
