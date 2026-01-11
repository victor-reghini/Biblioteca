package biblioteca.services;

import biblioteca.entities.User;
import biblioteca.repositories.UserRepository;
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
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1);
        user.setDataCadastro(new Date());
    }

    @Test
    void deve_criar_usuario_quando_data_cadastro_for_valida() {
        when(repository.save(user)).thenReturn(user);

        User result = service.create(user);

        assertNotNull(result);
        assertEquals(user, result);
        verify(repository).save(user);
    }

    @Test
    void deve_lancar_exception_quando_data_cadastro_for_futura() {
        user.setDataCadastro(new Date(System.currentTimeMillis() + 86400000));

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> service.create(user)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(
                "A data de cadastro não pode ser posterior a data atual",
                exception.getStatusText()
        );
        verify(repository, never()).save(any());
    }

    @Test
    void deve_atualizar_usuario() {
        when(repository.save(user)).thenReturn(user);

        User updated = service.update(user);

        assertNotNull(updated);
        verify(repository).save(user);
    }

    @Test
    void deve_buscar_usuario_por_id() {
        when(repository.getReferenceById(1)).thenReturn(user);

        User result = service.get(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository).getReferenceById(1);
    }

    @Test
    void deve_listar_todos_usuarios() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> users = service.getAll();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        verify(repository).findAll();
    }

    @Test
    void deve_excluir_usuario_por_id() {
        service.delete(1);

        verify(repository).deleteById(1);
        verifyNoMoreInteractions(repository);
    }
}
