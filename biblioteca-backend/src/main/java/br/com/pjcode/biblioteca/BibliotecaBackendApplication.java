package br.com.pjcode.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BibliotecaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaBackendApplication.class, args);
	}

}
