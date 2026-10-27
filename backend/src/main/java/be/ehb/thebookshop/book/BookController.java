
package be.ehb.thebookshop.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin //Laat verzoeken toe vanaf andere origins, bv een frontend op een andere poort
public class BookController {

    private final BookRepository bookRepository; //bewaart de verwijzing naar de repo

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Boek niet gevonden."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) {
        if (book.getIsbn() == null || book.getIsbn().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ISBN is verplicht.");
        }

        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Dit ISBN bestaat al.");
        }

        if (book.getPrice() == null || book.getPrice().signum() < 0
                || book.getStock() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ongeldige prijs of voorraad.");
        }

        return bookRepository.save(book);
    }
}
