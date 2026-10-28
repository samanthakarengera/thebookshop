
package be.ehb.thebookshop.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/** Toegang tot de opgeslagen boeken (DAO). */
public interface BookRepository extends JpaRepository<Book, Long> {
    /** Zoekt een boek op zijn unieke ISBN, om dubbele boeken te vermijden. */
    Optional<Book> findByIsbn(String isbn);
}
