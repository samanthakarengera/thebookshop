package be.ehb.thebookshop.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST-controller voor de boekencatalogus.
 * Alle endpoints staan onder {@code /api/books}.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository; //bewaart de verwijzing naar de repo

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Geeft alle boeken in de catalogus terug.
     *
     * @return de lijst van alle boeken met hun voorraad
     */
    @GetMapping
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    /**
     * Geeft één boek terug op basis van zijn id.
     *
     * @param id het id van het boek
     * @return het gevonden boek
     * @throws ResponseStatusException 404 als het boek niet bestaat
     */
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Boek niet gevonden."));
    }

    /**
     * Voegt een nieuw boek toe aan de catalogus.
     *
     * @param book het nieuwe boek
     * @return het opgeslagen boek
     * @throws ResponseStatusException 400 bij een ontbrekend ISBN of een ongeldige prijs of voorraad,
     *                                 409 als het ISBN al bestaat
     */
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