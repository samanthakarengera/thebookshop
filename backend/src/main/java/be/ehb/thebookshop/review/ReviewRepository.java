package be.ehb.thebookshop.review;

import be.ehb.thebookshop.book.Book;
import be.ehb.thebookshop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Toegang tot de opgeslagen reviews
public interface ReviewRepository extends JpaRepository<Review, Long> {

    //Alle reviews van een boek, de nieuwste eerst
    List<Review> findByBookOrderByCreatedAtDesc(Book book);

    //Controleert of de gebruiker dit boek al heeft beoordeeld
    boolean existsByUserAndBook(User user, Book book);
}