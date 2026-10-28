package be.ehb.thebookshop.review;

import java.time.LocalDateTime;


public record ReviewResponse(Long id, String authorName, int score,
                             String text, LocalDateTime createdAt) {
//De gegevens van een review zoals de frontend ze ontvangt

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getUser().getName(),
                review.getScore(),
                review.getText(),
                review.getCreatedAt());
    }
}