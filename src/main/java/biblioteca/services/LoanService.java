package biblioteca.services;

import biblioteca.entities.Loan;
import biblioteca.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    @Autowired
    private LoanRepository repository;

    public Loan create(Loan loan) {
        return repository.save(loan);
    }

    public Loan update(Loan loan) {
        return repository.save(loan);
    }
}
