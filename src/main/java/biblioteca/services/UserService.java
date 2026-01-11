package biblioteca.services;

import biblioteca.entities.User;
import biblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User create(User user) {
        if (user.getDataCadastro().after(new Date())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "A data de cadastro não pode ser posterior a data atual");
        }
        return repository.save(user);
    }

    public User update(User user) {
        return repository.save(user);
    }

    public User get(Integer id) {
        return repository.getReferenceById(id);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
