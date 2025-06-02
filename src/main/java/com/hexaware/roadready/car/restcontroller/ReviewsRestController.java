package com.hexaware.roadready.car.restcontroller;

import com.hexaware.roadready.car.entity.Reviews;
import com.hexaware.roadready.car.exception.ReviewNotFoundException;
import com.hexaware.roadready.car.service.IReviewsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Slf4j
public class ReviewsRestController {

    @Autowired
    private IReviewsService reviewsService;

    // Add a new review
    @PostMapping
    public ResponseEntity<Reviews> addReview(@RequestBody Reviews review) {
        log.info("API call: Add new review");
        Reviews savedReview = reviewsService.addReview(review);
        return ResponseEntity.ok(savedReview);
    }

    // Get review by ID
    @GetMapping("/{reviewId}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable Long reviewId)
            throws ReviewNotFoundException {
        log.info("API call: Get review by ID: {}", reviewId);
        Reviews review = reviewsService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    // Get reviews by car ID
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<Reviews>> getReviewsByCarId(@PathVariable Long carId) {
        log.info("API call: Get reviews by carId: {}", carId);
        List<Reviews> reviews = reviewsService.getReviewsByCarId(carId);
        return ResponseEntity.ok(reviews);
    }

    // Get reviews by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reviews>> getReviewsByUserId(@PathVariable Long userId) {
        log.info("API call: Get reviews by userId: {}", userId);
        List<Reviews> reviews = reviewsService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    // Delete review by ID
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId)
            throws ReviewNotFoundException {
        log.info("API call: Delete review by ID: {}", reviewId);
        reviewsService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted with ID: " + reviewId);
    }
}
