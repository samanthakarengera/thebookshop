package be.ehb.thebookshop.review;

import be.ehb.thebookshop.book.Book;
import be.ehb.thebookshop.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

//Een review van een gebruiker over een boek: een score van 1 tot 5 en een tekst.

@Entity
@Table(name = "reviews",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    private int score;

    @Column(name = "review_text", length = 1000)
    private String text;

    private LocalDateTime createdAt;

    public Review() {
    }

    public Review(User user, Book book, int score, String text) {
        this.user = user;
        this.book = book;
        this.score = score;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public User getUser() { return user; }

    public Book getBook() { return book; }

    public int getScore() { return score; }

    public String getText() { return text; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}