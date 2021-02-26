package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.Game;
import com.switchplaybackend.demo.model.Offer;
import com.switchplaybackend.demo.model.User;
import com.switchplaybackend.demo.repository.OfferRepository;
import com.switchplaybackend.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OfferControllerTest {

    @MockBean
    private OfferRepository offerRepository;

    @MockBean
    private UserRepository userRepository;


    @Autowired
    private OfferController offerController;

    private final Offer offer1 = new Offer();


    @BeforeEach
    void setup(){
       offer1.setDescription("description");
       offer1.setGame(new Game());
       offer1.setUser(new User());
    }

    @Test
    public void getAllOffersTest(){
        when(offerRepository.findAll()).thenReturn(Stream
        .of(offer1, offer1, offer1).collect(Collectors.toList()));

        assertEquals(3, offerController.getOffers().size());
    }

    @Test
    public void getOfferByIdTest(){
        UUID id = UUID.randomUUID();
        offer1.setId(id);

        when(offerRepository.findById(id)).thenReturn(java.util.Optional.of(offer1));

        assertEquals(offer1, offerController.getOfferById(id));
    }

    @Test
    public void addOfferTest() throws URISyntaxException {
        User user = new User();
        user.setId(UUID.randomUUID());
        offer1.setUser(user);

        when(offerRepository.save(offer1)).thenReturn(offer1);
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        assertEquals(offer1, offerController.addOffer(offer1, user.getId()).getBody());
    }


}
