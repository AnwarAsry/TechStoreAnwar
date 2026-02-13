package org.iths.techstore.Service;

import org.iths.techstore.Exceptions.ReviewNotFoundException;
import org.iths.techstore.Model.Review;
import org.iths.techstore.Repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    // Attribute
    private final ReviewRepository reviewRepository;

    // Constructor
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Get all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Get review by id
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("No review found with id " + id));
    }

    // Create new review
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    // Update existing review
    public Review updateReview(Long id, Review review) {
        Review found = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("No review found with id " + id));

        review.setId(found.getId());
        return reviewRepository.save(review);
    }

    // Delete existing review
    public void deleteReview(Long id) {
        reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("No review found with id " + id));

        reviewRepository.deleteById(id);
    }
}
