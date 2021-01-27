package com.switchplaybackend.demo.repository;

import com.switchplaybackend.demo.model.Deal;
import com.switchplaybackend.demo.util.DealStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DealRepository extends JpaRepository<Deal, UUID> {

    List<Deal> findByStatus (DealStatus dealStatus);
}
