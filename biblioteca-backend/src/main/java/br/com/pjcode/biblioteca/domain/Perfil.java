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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Long id;

    private String nome;
    
    public enum Values {

        ADMIN(1L),
        BASIC(2L);

        long id;

        Values(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }
}
