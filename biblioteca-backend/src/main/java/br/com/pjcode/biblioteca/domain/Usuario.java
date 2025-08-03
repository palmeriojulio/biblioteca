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
    @SequenceGenerator(name = "usurario_id_seq", sequenceName = "usurario_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.UUID, generator = "usurario_id_seq")
    @Column(name = "id_usurario")
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
}
