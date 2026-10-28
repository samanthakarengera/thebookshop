package be.ehb.thebookshop.reservation;

import be.ehb.thebookshop.book.Book;
import be.ehb.thebookshop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);

    boolean existsByUserAndBook(User user, Book book);
}
