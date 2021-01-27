package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DealController {

    @Autowired
    private DealRepository dealRepository;


    //get all the deals in progress (where status is in progress)

    //get all the completed deals (where status is accepted)

    //get all the rejected deals (where status is rejected)

    //get the deal where userWhoReceived id is...
    //the user who is receiving the offer will know which game he can receive and from which user



    //get the deal where userWhoSent id is...
    //user who is sending will know to whom he offered a game and also will have info about the game he wishes
}
