package br.com.pjcode.biblioteca.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.pjcode.biblioteca.dto.LoginRequestDto;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_usuario")
    private UUID id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
    		name = "user_perfil",
    		joinColumns = @JoinColumn(name = "id_usuario"),
    		inverseJoinColumns = @JoinColumn(name = "id_perfil")
    )    
    private Set<Perfil> perfil;
    
	/**
	 * Verifica se as credenciais de login estão corretas.
	 *
	 * @param loginRequestDto objeto contendo as credenciais de login
	 * @param passwordEncoder codificador de senha para verificar a senha
	 * @return true se o login estiver correto, false caso contrário
	 */
	public boolean isLoginCorrect(LoginRequestDto loginRequestDto, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(loginRequestDto.password(), this.password);
	}
}
