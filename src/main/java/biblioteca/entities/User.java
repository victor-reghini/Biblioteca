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
@Table(name = "USUARIO")
public class User {
    @Id
    @Column
    @NotNull
    Integer id;

    @Column
    @NotNull
    String nome;

    @Column
    @NotNull
    String email;

    @Column(name = "data_cadastro")
    @NotNull
    Date dataCadastro;

    @Column
    @NotNull
    Integer telefone;
}
