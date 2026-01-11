package biblioteca.controllers;

import biblioteca.entities.Book;
import biblioteca.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Book criarLivro() {
        Book book = new Book();
        book.setId(1);
        book.setTitulo("Livro de Teste");
        book.setAutor("Autor Teste");
        book.setDataPublicacao(new Date());
        return book;
    }

    @Test
    void deve_criar_livro() throws Exception {
        Book book = criarLivro();
        when(service.create(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Livro de Teste"));

        verify(service).create(any(Book.class));
    }

    @Test
    void deve_atualizar_livro() throws Exception {
        Book book = criarLivro();
        when(service.update(any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).update(any(Book.class));
    }

    @Test
    void deveB_buscar_livro_por_id() throws Exception {
        Book book = criarLivro();
        when(service.get(1)).thenReturn(book);

        mockMvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Livro de Teste"));

        verify(service).get(1);
    }

    @Test
    void deve_retornar_optional_vazio_quando_livro_nao_existir() throws Exception {
        when(service.get(99)).thenReturn(null);

        mockMvc.perform(get("/book/99"))
                .andExpect(status().isOk());

        verify(service).get(99);
    }

    @Test
    void deve_listar_todos_livros() throws Exception {
        when(service.getAll()).thenReturn(List.of(criarLivro()));

        mockMvc.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Livro de Teste"));

        verify(service).getAll();
    }

    @Test
    void deve_excluir_livro() throws Exception {
        doNothing().when(service).delete(1);

        mockMvc.perform(delete("/book/1"))
                .andExpect(status().isOk());

        verify(service).delete(1);
    }
}
