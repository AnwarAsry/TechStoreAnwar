package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.ReviewNotFoundException;
import org.iths.techstore.Exceptions.ReviewNotValidException;
import org.iths.techstore.Model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReviewValidatorTest {
    // Attribute
    ReviewValidator reviewValidator = new ReviewValidator();

    // Test review
    Review review;

    // Setup review
    @BeforeEach
    void setUp() {
        review = new Review(1L, "Samsung", 8, "Good product", LocalDate.now(), "Psy");
    }

    // Valid review
    @Test
    public void validateShouldNotThrowExceptionWhenReviewIsValid() {
        assertDoesNotThrow(() -> reviewValidator.validate(review));
    }

    // Review is null
    @Test
    public void validateShouldThrowExceptionWhenReviewIsNull() {
        // Arrange
        Review review = null;

        // Act / Assert
        assertThrows(ReviewNotFoundException.class, () -> reviewValidator.validate(review));
    }

    // Name is null
    @Test
    public void validateShouldThrowExceptionWhenReviewerNameIsNull() {
        // Arrange
        Review review = new Review(1L, "Samsung", 9, "Good", LocalDate.now(), null);

        // Act / Assert
        assertThrows(ReviewNotValidException.class, () -> reviewValidator.validate(review));
    }


    // Comment is null
    @Test
    public void validateShouldThrowExceptionWhenCommentIsNull() {
        // Arrange
        Review review = new Review(1L, "Samsung", 9, null, LocalDate.now(), "Psy");

        // Act / Assert
        assertThrows(ReviewNotValidException.class, () -> reviewValidator.validate(review));
    }

    // Rating below 0
    @Test
    public void validateShouldThrowExceptionWhenRatingIsBelowZero() {
        // Arrange
        Review review = new Review(1L, "Samsung", -10, "Very bad rating", LocalDate.now(), "Psy");

        // Act / Assert
        assertThrows(ReviewNotValidException.class, () -> reviewValidator.validate(review));
    }

    // Rating over 10
    @Test
    public void validateShouldThrowExceptionWhenRatingIsOverTen() {
        // Arrange
        Review review = new Review(1L, "Samsung", 11, "Great rating", LocalDate.now(), "Psy");

        // Act / Assert
        assertThrows(ReviewNotValidException.class, () -> reviewValidator.validate(review));
    }
}
