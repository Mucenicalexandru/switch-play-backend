package com.switchplaybackend.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "categories", catalog = "switch_play")
@Data
public class Category {

    protected Category() {

    }
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Game game;


}
