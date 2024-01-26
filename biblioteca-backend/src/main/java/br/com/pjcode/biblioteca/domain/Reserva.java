package br.com.pjcode.biblioteca.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reserva")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_reserva", nullable = false)
    private Long id;

    @Column(name = "data_reserva", nullable = false)
    private LocalDateTime dataReserva;

    @Column(name = "data_limite_reserva", nullable = false)
    private LocalDateTime dataLimiteReserva;

    @ManyToOne
    @JoinColumn(name = "livro_id_livro")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "leitor_id_leitor")
    private Leitor leitor;
    
}
