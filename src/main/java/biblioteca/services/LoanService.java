package biblioteca.services;

import biblioteca.entities.Loan;
import biblioteca.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class LoanService {
    @Autowired
    private LoanRepository repository;

    public Loan create(Loan loan) {
        // validar se o livro já está emprestado
        if (!repository.getActiveLoanByBookId(loan.getLivro().getId()).isEmpty()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "O livro se encontra indisponível para empréstimos no momento");
        }

        // Validando datas
        if (loan.getDataEmprestimo().after(new Date())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "A data de empréstimo não pode ser posterior a data atual");
        }

        if (nonNull(loan.getDataDevolucao()) && loan.getDataDevolucao().before(loan.getDataEmprestimo())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "A data de devolução não pode ser anterior a data de empréstimo");
        }

        if (nonNull(loan.getDataDevolucao()) && loan.getDataDevolucao().after(new Date())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "A data de devolução não pode ser posterior a data atual");
        }
        return repository.save(loan);
    }

    public Loan update(Loan loan) {
        return repository.save(loan);
    }

    public List<Loan> getAll() {
        return repository.findAll();
    }
}
