package br.com.pjcode.biblioteca.resource;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pjcode.biblioteca.dao.UsuarioRepository;
import br.com.pjcode.biblioteca.dto.LoginRequestDto;
import br.com.pjcode.biblioteca.dto.LoginResponseDto;

/**
 * @author Palmério Júlio 
 * Classe controller para gerenciar tokens de autenticação.
 */
@RestController
@RequestMapping("/biblioteca")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TokenController {
	
    // Injeção de dependência do JwtEncoder para gerar tokens JWT
	@Autowired
	private JwtEncoder jwtEncoder;
	
	// Injeção de dependência do repositório de usuários para autenticação
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/token")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
		
		var usuario = usuarioRepository.findByUsername(loginRequestDto.username());
		
		if (usuario.isEmpty() || !usuario.get().isLoginCorrect(loginRequestDto, bCryptPasswordEncoder)) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		var now = Instant.now();
		var expiresIn = 300L; // 5 minutos
		
		var claims = JwtClaimsSet.builder()
				.issuer("biblioteca")
				.subject(usuario.get().getUsername())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expiresIn))
				.build();
		
		var jwtValues = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
				
		return ResponseEntity.ok(new LoginResponseDto(jwtValues, expiresIn));
		
	}

}
