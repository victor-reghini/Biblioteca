package biblioteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "EMPRESTIMO")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="usuario_id")
    User usuario;

    @NotNull
    @ManyToOne
    @JoinColumn(name="livro_id")
    Book livro;

    @NotNull
    @Column(name = "data_emprestimo")
    Date dataEmprestimo;

    @Column(name = "data_devolucao")
    Date dataDevolucao;

    @NotNull
    String status;
}
