
package be.ehb.thebookshop.reservation;

import be.ehb.thebookshop.user.User;
import be.ehb.thebookshop.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ReservationController(
            ReservationService reservationService,
            ReservationRepository reservationRepository,
            UserRepository userRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    // Een boek reserveren.
    @PostMapping("/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation reserveBook(
            @PathVariable Long bookId,
            Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Log in.");
        }

        return reservationService.reserveBook(
                bookId,
                authentication.getName());
    }

    // De reserveringen van de ingelogde gebruiker ophalen.
    @GetMapping
    public List<Reservation> getMyReservations(
            Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Log in.");
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Gebruiker niet gevonden."));

        return reservationRepository.findByUser(user);
    }
}
