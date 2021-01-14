package com.switchplaybackend.demo.api;


import com.switchplaybackend.demo.model.Offer;
import com.switchplaybackend.demo.model.User;
import com.switchplaybackend.demo.repository.OfferRepository;
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
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/offers")
    public List<Offer> getOffers(){
        return offerRepository.findAll();
    }

    @PostMapping("/add-offer/{userID}")
    public ResponseEntity<Offer> addOffer(@RequestBody Offer offer ,@PathVariable UUID userID) throws URISyntaxException {
        User user = userRepository.findById(userID).get();
        offer.setUser(user);
        Offer offerToSave = offerRepository.save(offer);
        return ResponseEntity.created(new URI("/add-offer" + offerToSave.getId())).body(offerToSave);
    }

    @DeleteMapping("/remove-offer/{id}")
    public ResponseEntity<Offer> deleteOffer(@PathVariable UUID id){
        offerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-offer")
    public List<Offer> getOfferByName(@PathVariable String title){
        return offerRepository.getAllByGameTitle(title);
    }

}
