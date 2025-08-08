package br.com.pjcode.biblioteca.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	/**
	 * Chave pública RSA para validação de tokens JWT.
	 */
	@Value("${jwt.public.key}")
	private RSAPublicKey publicKey;
	
	/**
	 * Chave privada RSA para assinatura de tokens JWT.
	 */
	@Value("${jwt.private.key}")
	private RSAPrivateKey privateKey;
	
	/**
	 * Configuração de segurança HTTP para o aplicativo. Define as regras de
	 * autorização, desabilita CSRF, configura o servidor de recursos OAuth2 e
	 * define a política de sessão.
	 *
	 * @param http a instância de HttpSecurity para configuração
	 * @return a instância de SecurityFilterChain configurada
	 * @throws Exception se ocorrer um erro durante a configuração
	 */
	@Bean
	private SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
			.csrf(csrf -> csrf.disable())
			.oauth2ResourceServer(outh2 -> outh2.jwt(Customizer.withDefaults()))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
	    return http.build();
	} 
	
	/**
	 * Bean para decodificador JWT, que usa a chave pública RSA para validar tokens
	 * JWT.
	 *
	 * @return uma instância de JwtDecoder configurada com a chave pública
	 */
	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(publicKey).build();
	}
	
	/**
	 * Bean para codificador JWT, que usa a chave privada RSA para assinar tokens
	 * JWT.
	 *
	 * @return uma instância de JwtEncoder configurada com a chave privada
	 */
	@Bean
	public JwtEncoder jwtEncoder( ) {
		JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build();
		var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
		
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
