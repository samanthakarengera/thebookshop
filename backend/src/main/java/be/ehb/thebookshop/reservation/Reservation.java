package be.ehb.thebookshop.reservation;

import be.ehb.thebookshop.book.Book;
import be.ehb.thebookshop.user.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDateTime reservationDate;

    private LocalDate pickupDate;

    private String status;

    public Reservation() {
    }

    public Reservation(User user, Book book) {
        this.user = user;
        this.book = book;
        this.reservationDate = LocalDateTime.now();
        this.pickupDate = LocalDate.now().plusDays(2);
        this.status = "RESERVED";
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}