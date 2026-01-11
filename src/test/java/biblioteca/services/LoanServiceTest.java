package biblioteca.services;

import biblioteca.entities.Loan;
import biblioteca.entities.Book;
import biblioteca.repositories.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository repository;

    @InjectMocks
    private LoanService service;

    private Loan loan;
    private Book book;

    @BeforeEach
    void setup() {
        book = new Book();
        book.setId(1);

        loan = new Loan();
        loan.setLivro(book);
        loan.setDataEmprestimo(new Date());
        loan.setDataDevolucao(null);
    }

    @Test
    void deve_criar_emprestimo_quando_dados_forem_validos() {
        when(repository.getActiveLoanByBookId(1)).thenReturn(List.of());
        when(repository.save(loan)).thenReturn(loan);

        Loan result = service.create(loan);

        assertNotNull(result);
        verify(repository).getActiveLoanByBookId(1);
        verify(repository).save(loan);
    }

    @Test
    void deve_lancar_exception_quando_livro_ja_estiver_emprestado() {
        when(repository.getActiveLoanByBookId(1)).thenReturn(List.of(new Loan()));

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> service.create(loan)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(
                "O livro se encontra indisponível para empréstimos no momento",
                exception.getStatusText()
        );
        verify(repository, never()).save(any());
    }

    @Test
    void deve_lancar_exception_quando_data_emprestimo_for_futura() {
        loan.setDataEmprestimo(new Date(System.currentTimeMillis() + 86400000));
        when(repository.getActiveLoanByBookId(1)).thenReturn(List.of());

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> service.create(loan)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(
                "A data de empréstimo não pode ser posterior a data atual",
                exception.getStatusText()
        );
        verify(repository, never()).save(any());
    }

    @Test
    void deve_lancar_exception_quando_data_devolucao_for_anterior_ao_emprestimo() {
        loan.setDataDevolucao(
                new Date(loan.getDataEmprestimo().getTime() - 86400000)
        );
        when(repository.getActiveLoanByBookId(1)).thenReturn(List.of());

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> service.create(loan)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(
                "A data de devolução não pode ser anterior a data de empréstimo",
                exception.getStatusText()
        );
        verify(repository, never()).save(any());
    }

    @Test
    void deve_lancar_exception_quando_data_devolucao_for_futura() {
        loan.setDataDevolucao(new Date(System.currentTimeMillis() + 86400000));
        when(repository.getActiveLoanByBookId(1)).thenReturn(List.of());

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> service.create(loan)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(
                "A data de devolução não pode ser posterior a data atual",
                exception.getStatusText()
        );
        verify(repository, never()).save(any());
    }

    @Test
    void deve_atualizar_emprestimo() {
        when(repository.save(loan)).thenReturn(loan);

        Loan updated = service.update(loan);

        assertNotNull(updated);
        verify(repository).save(loan);
    }

    @Test
    void deve_listar_todos_emprestimos() {
        when(repository.findAll()).thenReturn(List.of(loan));

        List<Loan> loans = service.getAll();

        assertEquals(1, loans.size());
        verify(repository).findAll();
    }
}
