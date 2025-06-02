package com.hexaware.roadready.car.service;

import java.util.List;

import com.hexaware.roadready.car.entity.Reviews;
import com.hexaware.roadready.car.exception.ReviewNotFoundException;

public interface IReviewsService {

    Reviews addReview(Reviews review);

    List<Reviews> getReviewsByCarId(Long carId);

    List<Reviews> getReviewsByUserId(Long userId);

    Reviews getReviewById(Long reviewId) throws ReviewNotFoundException;

    void deleteReview(Long reviewId) throws ReviewNotFoundException;
}
