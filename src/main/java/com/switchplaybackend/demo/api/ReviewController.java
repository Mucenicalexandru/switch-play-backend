package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.Review;
import com.switchplaybackend.demo.model.User;
import com.switchplaybackend.demo.repository.ReviewRepository;
import com.switchplaybackend.demo.repository.UserRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/reviews")
    public Collection<Review> getReviews() {
        return reviewRepository.findAll();
    }

    @GetMapping("/get-all-stars-from-user-review/{userId}")
    public HashMap<Integer, Integer> getAllStarsFromReview(@PathVariable UUID userId) throws JSONException {
        HashMap<Integer, Integer> results = new HashMap<>();
        results.put(1, 0);
        results.put(2, 0);
        results.put(3, 0);
        results.put(4, 0);
        results.put(5, 0);

        List<Review> reviews = reviewRepository.findAllByUserWhoIsReceiving_Id(userId);
        List<Integer> result = reviews.stream().map(Review::getStarNumber).collect(Collectors.toList());

        for(Integer star : result){
            results.put(star, results.get(star) + 1);
        }

        return results;
    }


    @PostMapping("/add-review/{userId}")
    public ResponseEntity<Review> addReview(@Valid @PathVariable UUID userId, @RequestBody Review review) throws URISyntaxException {
        User userToUpdate = userRepository.findById(userId).get();
        review.setUserWhoIsReceiving(userToUpdate);

        java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        review.setDate(currentDate);

        Review result = reviewRepository.save(review);
        return ResponseEntity.created(new URI("/api/add-review" + result.getId())).body(result);
    }
}
