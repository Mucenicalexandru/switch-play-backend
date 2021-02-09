package com.switchplaybackend.demo.repository;

import com.switchplaybackend.demo.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DealRepository extends JpaRepository<Deal, UUID> {
    List<Deal> findAllByActiveUserIDAndGameListedId(UUID userID, UUID gameID);
}
