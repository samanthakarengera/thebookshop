
package be.ehb.thebookshop.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
/**
 * Een boek in de catalogus.
 * Het ISBN is uniek. De voorraad geeft aan hoeveel exemplaren nog beschikbaar zijn
 * en daalt wanneer een boek wordt gereserveerd.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isbn;
    /** De categorie van het boek, bijvoorbeeld THRILLER of SCIFI. Mag leeg zijn. */
    @Enumerated(EnumType.STRING)
    private BookCategory category;

    private String title;
    private String authors;

    @Column(length = 2000)
    private String description;

    private BigDecimal price;
    private int stock;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }
}
