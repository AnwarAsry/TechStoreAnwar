package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.ReviewNotFoundException;
import org.iths.techstore.Exceptions.ReviewNotValidException;
import org.iths.techstore.Model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewValidator {

    // Validate review
    public void validate(Review review) {
        checkReview(review);
        checkName(review.getReviewerName());
        checkComment(review.getComment());
        checkRating(review.getRating());
    }

    // Verify input
    private void checkReview(Review review) {
        if (review == null) {
            throw new ReviewNotFoundException("No review found");
        }
    }

    // Name required
    private void checkName(String name) {
        if (name == null) {
            throw new ReviewNotValidException("Reviewer name is null");
        }
    }

    // Comment required
    private void checkComment(String comment) {
        if (comment == null) {
            throw new ReviewNotValidException("No review comment found");
        }
    }

    // Rating valid
    private void checkRating(int rating) {
        if (rating < 0 || rating > 100) {
            throw new ReviewNotValidException("Review rating is not valid");
        }
    }
}
