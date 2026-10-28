
package be.ehb.thebookshop.reservation;

import be.ehb.thebookshop.book.Book;
import be.ehb.thebookshop.book.BookRepository;
import be.ehb.thebookshop.user.User;
import be.ehb.thebookshop.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            BookRepository bookRepository,
            UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Reservation reserveBook(Long bookId, String email) {

        // De gebruiker zoeken
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Gebruiker niet gevonden"));

        // Het boek zoeken
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Boek niet gevonden"));

        // Controleren of het boek nog beschikbaar is
        if (book.getStock() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Dit boek is niet in voorraad");
        }

        // Reserveer één exemplaar.
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);
        // De reservering opslaan.
        Reservation reservation = new Reservation(user, book);

        return reservationRepository.save(reservation);
    }
}
