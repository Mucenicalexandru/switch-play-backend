package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.Deal;
import com.switchplaybackend.demo.repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Transient;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@Transactional
@RequestMapping("/api")
public class DealController {

    @Autowired
    private DealRepository dealRepository;


    @GetMapping("/deals")
    public List<Deal> getAllDeals(){
        return dealRepository.findAll();
    }


    @GetMapping("/get-deal-by-user-and-game/{userID}/{gameID}")
    public List<Deal> getDealByUserAndGame(@PathVariable UUID userID, @PathVariable UUID gameID){
        List<Deal> deal = dealRepository.findAllByActiveUserIDAndGameListedId(userID, gameID);
        return deal;
    }


    @PostMapping("/add-deal")
    public ResponseEntity<Deal> addDeal(@Valid @RequestBody Deal deal) throws URISyntaxException {
        Deal result = dealRepository.save(deal);
        return ResponseEntity.created(new URI("/add-deal" + result.getId())).body(result);
    }


}
