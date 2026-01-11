package biblioteca.services;

import biblioteca.entities.Book;
import biblioteca.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public Book create(Book book) {
        if (book.getDataPublicacao().after(new Date())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "A data de publicação não pode ser posterior a data atual");
        }
        return repository.save(book);
    }

    public Book update(Book book) {
        return repository.save(book);
    }

    public Book get(Integer id) {
        return repository.getReferenceById(id);
    }

    public List<Book> getAll() {
        return repository.findAll();
    }

    public List<Book> getRecommendationByUser(Integer id) {
        return repository.getRecommendationByUserId(id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
