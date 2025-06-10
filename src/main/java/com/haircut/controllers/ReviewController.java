package com.haircut.controllers;

import com.haircut.models.Review;
import com.haircut.services.ReviewService;
import com.haircut.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<Review>> createReview(@RequestBody Review review) {
        Review createdReview = reviewService.createReview(review);
        return new ResponseEntity<>(ApiResponse.success("Review created successfully", createdReview), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Review>>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(ApiResponse.success("Reviews retrieved successfully", reviews), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Review>> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(review -> new ResponseEntity<>(ApiResponse.success("Review retrieved successfully", review), HttpStatus.OK))
                .orElse(new ResponseEntity<>(ApiResponse.error("Review not found"), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsByServiceId(@PathVariable Long serviceId) {
        List<Review> reviews = reviewService.getReviewsByServiceId(serviceId);
        return new ResponseEntity<>(ApiResponse.success("Reviews by service ID retrieved successfully", reviews), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsByCustomerId(@PathVariable Long customerId) {
        List<Review> reviews = reviewService.getReviewsByCustomerId(customerId);
        return new ResponseEntity<>(ApiResponse.success("Reviews by customer ID retrieved successfully", reviews), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long id) {
        if (reviewService.deleteReview(id)) {
            return new ResponseEntity<>(ApiResponse.success("Review deleted successfully", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.error("Review not found"), HttpStatus.NOT_FOUND);
    }
} 