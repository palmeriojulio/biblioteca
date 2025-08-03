package br.com.pjcode.biblioteca.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "perfil")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Perfil implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "perfil_id_seq", sequenceName = "perfil_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_id_seq")
    @Column(name = "id_perfil")
    private Long id;

    private String nome; // ADMIN, USER, etc.
    
	public enum TipoPerfil {
		ADMIN(1L), // ID do perfil ADMIN), 
		USER(2L); // ID do perfil USER)
		
		long perfilId;
		
		TipoPerfil(long perfilId) {
            this.perfilId = perfilId;
        }
		
		public long getPerfilId() {
			return perfilId;
        }
	}
}
