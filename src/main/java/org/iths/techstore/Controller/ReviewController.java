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

    // Create new review
    @GetMapping("/new")
    public String createReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "new-review";
    }

    // Save new review
    @PostMapping("/save")
    public String saveReview(@ModelAttribute Review review) {
        reviewService.createReview(review);
        return "redirect:/reviews";
    }

    // Edit existing review
    @GetMapping("/edit/{id}")
    public String editReviewForm(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReviewById(id));
        return "edit-review";
    }

    // Update existing review
    @PostMapping("/update/{id}")
    public String updateReview(@PathVariable Long id, @ModelAttribute Review review) {
        reviewService.updateReview(id, review);
        return "redirect:/reviews";
    }

    // Remove existing review
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}
