package biblioteca.repositories;

import biblioteca.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    @Query(value = "SELECT l FROM Loan l WHERE l.livro.id = :bookId AND l.status <> 'DEVOLVIDO' AND l.dataDevolucao is null")
    List<Loan> getActiveLoanByBookId(@Param("bookId") Integer bookId);
}
