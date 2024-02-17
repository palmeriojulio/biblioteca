package br.com.pjcode.biblioteca.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import br.com.pjcode.biblioteca.domain.enums.Status;
import jakarta.persistence.*;
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
    @SequenceGenerator(name = "id_emprestimo_seq", sequenceName = "id_emprestimo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_emprestimo_seq")
    @Column(name = "id_emprestimo", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "emprestimo_livro",
            joinColumns = {@JoinColumn(name="id_emprestimo")},
            inverseJoinColumns = {@JoinColumn(name="id_livro")})
    private List<Livro> livros;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_leitor", referencedColumnName = "id_leitor")
    private Leitor leitor;

    @Column(name = "data_do_emprestimo", nullable = false)
    private LocalDateTime dataDoEmprestimo;

    @Column(name = "data_da_devolucao", nullable = false)
    private LocalDateTime dataDaDevolucao;

    @Column(name = "status_do_emprestimo", nullable = false)
    private Status status;

    public Emprestimo(Long id, LocalDateTime dataDoEmprestimo, LocalDateTime dataDaDevolucao, List<Livro> convertList, Leitor leitor) {
    }
}
