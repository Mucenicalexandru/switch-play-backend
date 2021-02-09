package com.switchplaybackend.demo.repository;

import com.switchplaybackend.demo.model.OfferAdded;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddedOfferRepository extends JpaRepository<OfferAdded, UUID> {

    List<OfferAdded> getAllByGameTitle(String title);
    List<OfferAdded> getAllByUser_Id (UUID id);


}
