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
@Table(name = "LIVRO")
public class Book {
    @Id
    @Column
    @NotNull
    Integer id;

    @Column
    @NotNull
    String titulo;

    @Column
    @NotNull
    String autor;

    @Column
    @NotNull
    String isbn;

    @Column(name = "dataPublicacao")
    @NotNull
    Date dataPublicacao;

    @Column
    @NotNull
    String categoria;
}
