package biblioteca.controllers;

import biblioteca.entities.Book;
import biblioteca.entities.Loan;
import biblioteca.entities.User;
import biblioteca.services.LoanService;
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

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Loan criarEmprestimo() {
        User user = new User();
        user.setId(1);
        user.setNome("Usuário Teste");

        Book book = new Book();
        book.setId(1);
        book.setTitulo("Livro Teste");

        Loan loan = new Loan();
        loan.setId(1);
        loan.setUsuario(user);
        loan.setLivro(book);
        loan.setDataEmprestimo(new Date());
        loan.setDataDevolucao(null);

        return loan;
    }

    @Test
    void deve_criar_emprestimo() throws Exception {
        Loan loan = criarEmprestimo();
        when(service.create(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(post("/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.livro.id").value(1))
                .andExpect(jsonPath("$.usuario.id").value(1));

        verify(service).create(any(Loan.class));
    }

    @Test
    void deve_atualizar_emprestimo() throws Exception {
        Loan loan = criarEmprestimo();
        when(service.update(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(put("/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).update(any(Loan.class));
    }

    @Test
    void deve_listar_todos_os_emprestimos() throws Exception {
        when(service.getAll()).thenReturn(List.of(criarEmprestimo()));

        mockMvc.perform(get("/loan/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].livro.id").value(1));

        verify(service).getAll();
    }
}
