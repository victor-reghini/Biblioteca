package biblioteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "USUARIO")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    String nome;

    @NotNull
    String email;

    @NotNull
    @Column(name = "data_cadastro")
    Date dataCadastro;

    @NotNull
    BigInteger telefone;
}
