package com.switchplaybackend.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "reviews", catalog = "switch_play")
public class Review {

    public Review(){

    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User userWhoIsReceiving;

    private String title;
    private int starNumber;
    private String review;
    private Date date;
    private String userWhoIsGivingName;


}
