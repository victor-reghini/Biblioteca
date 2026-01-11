package biblioteca.repositories;

import biblioteca.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT b FROM Book b " +
                    "WHERE " +
                        "NOT EXISTS (SELECT 1 FROM Loan l WHERE l.usuario.id = :userId) " +
                        "OR (" +
                            "b.id NOT IN (SELECT l.livro.id FROM Loan l WHERE l.usuario.id = :userId) " +
                            "AND b.categoria IN (" +
                                "SELECT DISTINCT b2.categoria FROM Book b2 " +
                                "WHERE b2.id in (SELECT l.livro.id FROM Loan l WHERE l.usuario.id = :userId)" +
                            ")" +
                        ")"
            )
    List<Book> getRecommendationByUserId(@Param("userId") Integer userId);
}
