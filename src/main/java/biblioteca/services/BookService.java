package biblioteca.services;

import biblioteca.entities.Book;
import biblioteca.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public Book create(Book book) {
        return repository.save(book);
    }

    public Book update(Book book) {
        return repository.save(book);
    }

    public Book get(Integer id) {
        return repository.getReferenceById(id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
