package br.com.pjcode.biblioteca.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.pjcode.biblioteca.domain.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id_emprestimo;

    @ManyToOne
    @JoinColumn(name = "livro_id_livro")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "leitor_id_leitor")
    private Leitor leitor;

    @Column(name = "data_do_emprestimo", nullable = false)
    private LocalDateTime dataDoEmprestimo;

    @Column(name = "data_da_devolucao", nullable = false)
    private LocalDateTime dataDaDevolucao;

    @Column(name = "status_do_emprestimo", nullable = false)
    private Status status;

}
