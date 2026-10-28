package be.ehb.thebookshop.review;

import be.ehb.thebookshop.book.Book;
import be.ehb.thebookshop.book.BookRepository;
import be.ehb.thebookshop.reservation.ReservationRepository;
import be.ehb.thebookshop.user.User;
import be.ehb.thebookshop.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         BookRepository bookRepository,
                         UserRepository userRepository,
                         ReservationRepository reservationRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    // Geeft alle reviews van een boek geeft 404 als het boek niet bestaat

    public List<ReviewResponse> getReviewsForBook(Long bookId) {
        Book book = findBook(bookId);

        return reviewRepository.findByBookOrderByCreatedAtDesc(book)
                .stream()
                .map(ReviewResponse::from)
                .toList();
    }


    @Transactional
    public ReviewResponse addReview(Long bookId, String email, ReviewRequest request) {

        if (request.score() == null || request.score() < 1 || request.score() > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "De score moet tussen 1 en 5 liggen.");
        }

        if (request.text() == null || request.text().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Schrijf ook een tekst bij je review.");
        }

        if (request.text().length() > 1000) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "De tekst mag maximaal 1000 tekens bevatten.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Gebruiker niet gevonden."));

        Book book = findBook(bookId);

        if (!reservationRepository.existsByUserAndBook(user, book)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Je kan enkel een review schrijven voor een boek dat je hebt gekocht.");
        }

        if (reviewRepository.existsByUserAndBook(user, book)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Je hebt dit boek al beoordeeld.");
        }

        Review review = new Review(user, book, request.score(), request.text().trim());

        return ReviewResponse.from(reviewRepository.save(review));
    }

    private Book findBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Boek niet gevonden."));
    }
}