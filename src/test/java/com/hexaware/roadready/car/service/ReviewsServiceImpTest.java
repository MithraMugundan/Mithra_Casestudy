package com.hexaware.roadready.car.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.hexaware.roadready.car.entity.Cars;
import com.hexaware.roadready.car.entity.Reviews;
import com.hexaware.roadready.car.entity.Users;
import com.hexaware.roadready.car.exception.ReviewNotFoundException;
import com.hexaware.roadready.car.repository.ReviewsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReviewsServiceImpTest {

    @Mock
    private ReviewsRepository reviewsRepository;

    @InjectMocks
    private ReviewsServiceImp reviewsServiceImp;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReview() {
        Reviews review = new Reviews();
        Cars car = new Cars();
        car.setCarId(1L);
        review.setCar(car);
        review.setReviewId(100L);

        when(reviewsRepository.save(review)).thenReturn(review);

        Reviews saved = reviewsServiceImp.addReview(review);

        assertNotNull(saved);
        assertEquals(100L, saved.getReviewId());
        verify(reviewsRepository).save(review);
    }

    @Test
    void testGetReviewsByCarId() {
        Cars car = new Cars();
        car.setCarId(1L);

        Reviews r1 = new Reviews();
        r1.setReviewId(1L);
        r1.setCar(car);

        Reviews r2 = new Reviews();
        r2.setReviewId(2L);
        r2.setCar(car);

        when(reviewsRepository.findByCarCarId(1L)).thenReturn(Arrays.asList(r1, r2));

        List<Reviews> reviews = reviewsServiceImp.getReviewsByCarId(1L);

        assertEquals(2, reviews.size());
        assertEquals(1L, reviews.get(0).getCar().getCarId());
    }

    @Test
    void testGetReviewsByUserId() {
        Users user = new Users();
        user.setUserId(1L);

        Reviews r1 = new Reviews();
        r1.setReviewId(1L);
        r1.setUser(user);

        Reviews r2 = new Reviews();
        r2.setReviewId(2L);
        r2.setUser(user);

        when(reviewsRepository.findByUserUserId(1L)).thenReturn(Arrays.asList(r1, r2));

        List<Reviews> reviews = reviewsServiceImp.getReviewsByUserId(1L);

        assertEquals(2, reviews.size());
        assertEquals(1L, reviews.get(0).getUser().getUserId());
    }

    @Test
    void testGetReviewById_Found() throws ReviewNotFoundException {
        Reviews review = new Reviews();
        review.setReviewId(10L);

        when(reviewsRepository.findById(10L)).thenReturn(Optional.of(review));

        Reviews found = reviewsServiceImp.getReviewById(10L);

        assertNotNull(found);
        assertEquals(10L, found.getReviewId());
    }

    @Test
    void testGetReviewById_NotFound() {
        when(reviewsRepository.findById(99L)).thenReturn(Optional.empty());

        ReviewNotFoundException ex = assertThrows(ReviewNotFoundException.class, () -> {
            reviewsServiceImp.getReviewById(99L);
        });

        assertTrue(ex.getMessage().contains("Review not found"));
    }

    @Test
    void testDeleteReview_Success() throws ReviewNotFoundException {
        Long id = 5L;

        when(reviewsRepository.existsById(id)).thenReturn(true);
        doNothing().when(reviewsRepository).deleteById(id);

        assertDoesNotThrow(() -> {
            reviewsServiceImp.deleteReview(id);
        });

        verify(reviewsRepository).deleteById(id);
    }

    @Test
    void testDeleteReview_NotFound() {
        Long id = 99L;

        when(reviewsRepository.existsById(id)).thenReturn(false);

        ReviewNotFoundException ex = assertThrows(ReviewNotFoundException.class, () -> {
            reviewsServiceImp.deleteReview(id);
        });

        assertTrue(ex.getMessage().contains("Cannot delete"));
    }
}
