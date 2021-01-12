package com.switchplaybackend.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "games", catalog = "switch_play")
@Data
public class Game {

    protected Game() {

    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String title;
    private String platform;
    private String picture;

    @OneToMany(mappedBy = "game")
    @JsonManagedReference(value = "game")
    private List<Category> category;

}
