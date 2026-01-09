package biblioteca.services;

import biblioteca.entities.User;
import biblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User create(User user) {
        return repository.save(user);
    }

    public User update(User user) {
        return repository.save(user);
    }

    public User get(Integer id) {
        return repository.getReferenceById(id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
