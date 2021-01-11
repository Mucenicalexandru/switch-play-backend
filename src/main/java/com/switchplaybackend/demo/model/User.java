package com.switchplaybackend.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String phone;
    private String town;
    private String country;
    private String console;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Category> favouriteCategories;

    private Date registrationDate;

}
