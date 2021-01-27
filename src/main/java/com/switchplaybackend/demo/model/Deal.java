package com.switchplaybackend.demo.model;

import com.switchplaybackend.demo.util.DealStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "deals", catalog = "switch_play")
@Data
public class Deal {

    protected Deal(){

    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    private User userWhoSent; //user who is sending the offer
    @OneToOne(cascade = CascadeType.ALL)
    private Game gameSent; //the given game of the user who sends the offer

    @OneToOne(cascade = CascadeType.ALL)
    private User userWhoReceived; //user who is receiving the offer
    @OneToOne(cascade = CascadeType.ALL)
    private Game gameWhoReceived; //the game of the user who is receiving the offer

    private Date date;

    private DealStatus status = DealStatus.PENDING; // pending by default. Can be accepted or rejected also

}
