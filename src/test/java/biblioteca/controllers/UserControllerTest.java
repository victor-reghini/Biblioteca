package biblioteca.controllers;

import biblioteca.entities.User;
import biblioteca.services.UserService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    private User criarUsuario() {
        User user = new User();
        user.setId(1);
        user.setNome("Usuário Teste");
        user.setDataCadastro(new Date());
        return user;
    }

    @Test
    void deve_criar_usuario() throws Exception {
        User user = criarUsuario();
        when(service.create(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Usuário Teste"));

        verify(service).create(any(User.class));
    }

    @Test
    void deve_atualizar_usuario() throws Exception {
        User user = criarUsuario();
        when(service.update(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).update(any(User.class));
    }

    @Test
    void deve_buscar_usuario_por_id() throws Exception {
        User user = criarUsuario();
        when(service.get(1)).thenReturn(user);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Usuário Teste"));

        verify(service).get(1);
    }

    @Test
    void deve_retornar_optional_vazio_quando_usuario_nao_existir() throws Exception {
        when(service.get(99)).thenReturn(null);

        mockMvc.perform(get("/user/99"))
                .andExpect(status().isOk());

        verify(service).get(99);
    }

    @Test
    void deve_listar_todos_usuarios() throws Exception {
        when(service.getAll()).thenReturn(List.of(criarUsuario()));

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(service).getAll();
    }

    @Test
    void deve_excluir_usuario() throws Exception {
        doNothing().when(service).delete(1);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());

        verify(service).delete(1);
    }
}
