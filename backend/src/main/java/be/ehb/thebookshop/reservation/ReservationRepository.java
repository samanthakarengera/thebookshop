package be.ehb.thebookshop.reservation;

import be.ehb.thebookshop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);
}