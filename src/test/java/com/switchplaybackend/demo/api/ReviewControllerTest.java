package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.Review;
import com.switchplaybackend.demo.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class ReviewControllerTest {

    @MockBean
    private ReviewRepository repository;

    @Autowired
    private ReviewController reviewController;

    public Review review = new Review();

    @BeforeEach
    void setup(){
        review.setId(UUID.randomUUID());
        review.setStarNumber(5);
        review.setTitle("Title");
        review.setUserWhoIsGivingName("First Name");
    }

    @Test
    void getReviews() {
        when(repository.findAll()).thenReturn(Stream
                .of(review, review).collect(Collectors.toList()));

        assertEquals(2, reviewController.getReviews().size());
    }


}