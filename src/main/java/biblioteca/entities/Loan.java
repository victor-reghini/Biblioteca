package biblioteca.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column
    @NotNull
    Integer id;

    @Column(name = "usuario_id")
    @NotNull
    Integer usuarioId;

    @Column(name = "livro_id")
    @NotNull
    Integer livroId;

    @Column(name = "data_emprestimo")
    @NotNull
    Date dataEmprestimo;

    @Column(name = "data_devolucao")
    @NotNull
    Date dataDevolucao;

    @Column
    @NotNull
    String status;
}
