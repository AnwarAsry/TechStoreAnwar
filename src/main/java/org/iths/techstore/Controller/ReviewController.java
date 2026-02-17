package org.iths.techstore.Controller;

import org.iths.techstore.Model.Review;
import org.iths.techstore.Service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    // Atribute
    private final ReviewService reviewService;

    // Constructor
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Get all reviews
    @GetMapping
    public String getAllReviews(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "reviews";
    }

    // Get review
    @GetMapping("/{id}")
    public String getReview(Model model, @PathVariable long id) {
        model.addAttribute("review", reviewService.getReviewById(id));
        return "review";
    }

    // Create new review
    @GetMapping("/new")
    public String createReviewForm() {
        return "add-review";
    }

    // Save new review
    @PostMapping
    public String saveReview(@ModelAttribute Review review) {
        Review newReview = reviewService.createReview(review);
        return "redirect:/reviews/" + newReview.getId();
    }

    // Update existing review
    @GetMapping("/{id}/edit")
    public String updateReview(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReviewById(id));
        return "edit-review";
    }

    // Submission of edited review
    @PutMapping("/{id}")
    public String updateReview(@PathVariable Long id, @ModelAttribute Review review) {
        Review updatedReview = reviewService.updateReview(id, review);
        return "redirect:/reviews/" + updatedReview.getId();
    }

    // Remove existing review
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}
