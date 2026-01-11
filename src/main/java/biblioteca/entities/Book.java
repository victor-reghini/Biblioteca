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
@Table(name = "LIVRO")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    String titulo;

    @NotNull
    String autor;

    @NotNull
    BigInteger isbn;

    @NotNull
    @Column(name = "data_publicacao")
    Date dataPublicacao;

    @NotNull
    String categoria;
}
