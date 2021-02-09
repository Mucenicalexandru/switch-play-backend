package com.switchplaybackend.demo.api;


import com.switchplaybackend.demo.model.OfferAdded;
import com.switchplaybackend.demo.model.User;
import com.switchplaybackend.demo.repository.AddedOfferRepository;
import com.switchplaybackend.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OfferAddedController {

    @Autowired
    private AddedOfferRepository addedOfferRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/offers")
    public List<OfferAdded> getOffers(){
        return addedOfferRepository.findAll();
    }

    @GetMapping("/offer-by-id/{offerId}")
    public OfferAdded getOfferById(@PathVariable UUID offerId){
        return addedOfferRepository.findById(offerId).get();
    }

    @PostMapping("/add-offer/{userID}")
    public ResponseEntity<OfferAdded> addOffer(@RequestBody OfferAdded offerAdded, @PathVariable UUID userID) throws URISyntaxException {
        User user = userRepository.findById(userID).get();
        offerAdded.setUser(user);
        OfferAdded offerAddedToSave = addedOfferRepository.save(offerAdded);
        return ResponseEntity.created(new URI("/add-offer" + offerAddedToSave.getId())).body(offerAddedToSave);
    }

    @DeleteMapping("/remove-offer/{id}")
    public ResponseEntity<OfferAdded> deleteOffer(@PathVariable UUID id){
        addedOfferRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-offer/{title}")
    public List<OfferAdded> getOfferByName(@PathVariable String title){
        return addedOfferRepository.getAllByGameTitle(title);

    }

}
