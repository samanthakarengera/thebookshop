package be.ehb.thebookshop.config;

import be.ehb.thebookshop.book.Book;
import be.ehb.thebookshop.book.BookRepository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * Laadt bij het opstarten voorbeeldboeken uit books.json,
 * maar alleen als de catalogus nog leeg is.
 */
@Component
public class BookDataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;

    public BookDataInitializer(BookRepository bookRepository,
                               ObjectMapper objectMapper) {
        this.bookRepository = bookRepository;
        this.objectMapper = objectMapper;
    }
    /** Laadt de boeken uit books.json, maar alleen als de catalogus nog leeg is. */
    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() > 0) {
            return;
        }

        try (InputStream input = new ClassPathResource("books.json").getInputStream()) {
            List<Book> books = objectMapper.readValue(
                    input, new TypeReference<List<Book>>() { });
            bookRepository.saveAll(books);
        }
    }
}