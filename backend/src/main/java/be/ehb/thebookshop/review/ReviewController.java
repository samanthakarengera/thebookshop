package be.ehb.thebookshop.review;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//REST-endpoints voor de reviews van een boek
@RestController
@RequestMapping("/api/books/{bookId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewResponse> getReviews(@PathVariable Long bookId) {
        return reviewService.getReviewsForBook(bookId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse addReview(@PathVariable Long bookId,
                                    @RequestBody ReviewRequest request,
                                    Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Log in.");
        }

        return reviewService.addReview(bookId, authentication.getName(), request);
    }
}