package biblioteca.controllers;

import biblioteca.entities.User;
import biblioteca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping
    User create(@RequestBody User user) {
        return service.create(user);
    }

    @PutMapping
    User update(@RequestBody User user) {
        return service.update(user);
    }

    @GetMapping(path = "/{id}")
    Optional<User> getById(@PathVariable Integer id) {
        return Optional.ofNullable(service.get(id));
    }

    @GetMapping(path = "/list")
    List<User> listAll() {
        return service.getAll();
    }

    @DeleteMapping(path = "/{id}")
    void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
