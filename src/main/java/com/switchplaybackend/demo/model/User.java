package com.switchplaybackend.demo.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users", catalog = "switch_play")
public class User {

    protected User(){

    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String town;
    private String country;
    private String console;

    @OneToMany(targetEntity = Category.class,cascade = CascadeType.ALL)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private List<Category> favouriteCategories;

    @OneToMany(targetEntity = Offer.class,cascade = CascadeType.ALL)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private List<Offer> activeOffers;


    private Date registrationDate;

}
