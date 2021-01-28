package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.Deal;
import com.switchplaybackend.demo.repository.DealRepository;
import com.switchplaybackend.demo.util.DealStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DealController {

    @Autowired
    private DealRepository dealRepository;


//    get all the deals in progress (where status is in progress)
    @GetMapping("/deals-in-progress")
    public List<Deal> getDealsInProgress(){
        return dealRepository.findByStatus(DealStatus.PENDING);
    }


    //get all the completed deals (where status is accepted)
    @GetMapping("/deals-completed")
    public List<Deal> getDealsCompleted(){
        return dealRepository.findByStatus(DealStatus.ACCEPTED);
    }


    //get all the rejected deals (where status is rejected)
    @GetMapping("/deals-rejected")
    public List<Deal> getDealsRejected(){
        return dealRepository.findByStatus(DealStatus.REJECTED);
    }

    //get the deal where userWhoReceived id is...
    //the user who is receiving the offer will know which game he can receive and from which user
    @GetMapping("/deal-by-user-who-received-the-offer/{userId}")
    public Deal getDealByUserWhoReceived(@PathVariable UUID userId){
        return dealRepository.findByUserWhoReceived_Id(userId);
    }

    //get the deal where userWhoSent id is...
    //user who is sending will know to whom he offered a game and also will have info about the game he wishes
    @GetMapping("/deal-by-user-who-sent-the-offer/{userId}")
    public Deal getDealByUserWhoSent(@PathVariable UUID userId){
        return dealRepository.findByUserWhoSent_Id(userId);
    }

    //when click on "Make an offer" - we add the deal with 2 users, 2 games, status and date
    @PostMapping("/add-deal")
    public ResponseEntity<Deal> addDeal(@RequestBody Deal deal) throws URISyntaxException {
        Deal result = dealRepository.save(deal);
        return ResponseEntity.created(new URI("/api/add-deal" + result.getId())).body(result);
    }
}
