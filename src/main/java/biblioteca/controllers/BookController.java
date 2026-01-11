package biblioteca.controllers;

import biblioteca.entities.Book;
import biblioteca.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService service;

    @PostMapping
    Book create(@RequestBody Book book) {
        return service.create(book);
    }

    @PutMapping
    Book update(@RequestBody Book book) {
        return service.update(book);
    }

    @GetMapping(path = "/{id}")
    Optional<Book> getById(@PathVariable Integer id) {
        return Optional.ofNullable(service.get(id));
    }

    @GetMapping(path = "/list")
    List<Book> listAll() {
        return service.getAll();
    }

    @DeleteMapping(path = "/{id}")
    void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
