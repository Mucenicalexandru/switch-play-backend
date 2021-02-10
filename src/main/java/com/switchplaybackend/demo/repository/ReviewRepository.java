package com.switchplaybackend.demo.repository;

import com.switchplaybackend.demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByUserWhoIsReceiving_Id (UUID userId);
}
