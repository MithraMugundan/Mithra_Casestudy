package com.hexaware.roadready.car.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.roadready.car.entity.Reviews;
import com.hexaware.roadready.car.exception.ReviewNotFoundException;
import com.hexaware.roadready.car.repository.ReviewsRepository;

import lombok.extern.slf4j.Slf4j;

/*
 * Name        : Mithra
 * Date        : May 28, 2025 
 * Description : Manages reviews including adding, updating, and fetching user reviews related to services or products.
 */

@Service
@Slf4j
public class ReviewsServiceImp implements IReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Override
    public Reviews addReview(Reviews review) {
        log.info("Adding new review for carId: {}", review.getCar().getCarId());
        Reviews savedReview = reviewsRepository.save(review);
        log.info("Review added with ID: {}", savedReview.getReviewId());
        return savedReview;
    }

    @Override
    public List<Reviews> getReviewsByCarId(Long carId) {
        log.info("Fetching reviews for carId: {}", carId);
        return reviewsRepository.findByCarCarId(carId);
    }

    @Override
    public List<Reviews> getReviewsByUserId(Long userId) {
        log.info("Fetching reviews by userId: {}", userId);
        return reviewsRepository.findByUserUserId(userId);
    }

    @Override
    public Reviews getReviewById(Long reviewId) throws ReviewNotFoundException {
        log.info("Fetching review with ID: {}", reviewId);
        return reviewsRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.error("Review not found with ID: {}", reviewId);
                    return new ReviewNotFoundException("Review not found with id: " + reviewId);
                });
    }

    @Override
    public void deleteReview(Long reviewId) throws ReviewNotFoundException {
        log.info("Deleting review with ID: {}", reviewId);
        if (!reviewsRepository.existsById(reviewId)) {
            log.error("Review not found with ID: {}", reviewId);
            throw new ReviewNotFoundException("Cannot delete. Review not found with id: " + reviewId);
        }
        reviewsRepository.deleteById(reviewId);
        log.info("Review deleted with ID: {}", reviewId);
    }
}
