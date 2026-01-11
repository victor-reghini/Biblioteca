package biblioteca.services;

import biblioteca.entities.Book;
import biblioteca.repositories.BookRepository;
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
class BookServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    private Book book;

    @BeforeEach
    void setup() {
        book = new Book();
        book.setId(1);
        book.setTitulo("Livro de Teste");
        book.setDataPublicacao(new Date());
    }

    @Test
    void deve_criar_livro_quando_data_publicacao_for_valida() {
        when(repository.save(book)).thenReturn(book);

        Book result = service.create(book);

        assertNotNull(result);
        assertEquals(book, result);
        verify(repository).save(book);
    }

    @Test
    void deve_lancar_exception_quando_data_publicacao_for_futura() {
        book.setDataPublicacao(new Date(System.currentTimeMillis() + 86400000));

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> service.create(book)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(
                "A data de publicação não pode ser posterior a data atual",
                exception.getStatusText()
        );
        verify(repository, never()).save(any());
    }

    @Test
    void deve_atualizar_livro() {
        when(repository.save(book)).thenReturn(book);

        Book updated = service.update(book);

        assertNotNull(updated);
        verify(repository).save(book);
    }

    @Test
    void deve_buscar_livro_por_id() {
        when(repository.getReferenceById(1)).thenReturn(book);

        Book result = service.get(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository).getReferenceById(1);
    }

    @Test
    void deve_listar_todos_livros() {
        when(repository.findAll()).thenReturn(List.of(book));

        List<Book> livros = service.getAll();

        assertFalse(livros.isEmpty());
        assertEquals(1, livros.size());
        verify(repository).findAll();
    }

    @Test
    void deve_excluir_livro_por_id() {
        service.delete(1);

        verify(repository).deleteById(1);
        verifyNoMoreInteractions(repository);
    }
}
