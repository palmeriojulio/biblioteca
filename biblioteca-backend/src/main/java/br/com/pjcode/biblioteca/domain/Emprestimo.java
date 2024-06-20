package br.com.pjcode.biblioteca.domain;

import br.com.pjcode.biblioteca.constants.StatusEmprestimoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "emprestimo_id_seq", sequenceName = "emprestimo_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emprestimo_id_seq")
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

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDateTime dataDoEmprestimo;

    @Column(name = "data_devolucao_prevista", nullable = false)
    private LocalDateTime dataDevolucaoPrevista;

    @Column(name = "data_devolucao_real", nullable = false)
    private LocalDateTime dataDevolucaoReal;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private StatusEmprestimoEnum status;

    // Construtor explícito
    public Emprestimo(
            Long id,
            LocalDateTime dataDoEmprestimo,
            LocalDateTime dataDevolucaoPrevista,
            LocalDateTime dataDevolucaoReal,
            StatusEmprestimoEnum status,
            List<Livro> convertList,
            Leitor leitor)
    {
    }
}
