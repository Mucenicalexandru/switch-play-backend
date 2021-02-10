package com.switchplaybackend.demo.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String town;
    private String country;
    private String console;

    @OneToMany(mappedBy = "userWhoIsReceiving", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    private Date registrationDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", town='" + town + '\'' +
                ", country='" + country + '\'' +
                ", console='" + console + '\'' +
                ", registrationDate=" + registrationDate +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getPhone(), user.getPhone()) &&
                Objects.equals(getTown(), user.getTown()) &&
                Objects.equals(getCountry(), user.getCountry()) &&
                Objects.equals(getConsole(), user.getConsole()) &&
                Objects.equals(getRegistrationDate(), user.getRegistrationDate()) &&
                Objects.equals(getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getPassword(), getPhone(), getTown(), getCountry(), getConsole(), getRegistrationDate(), getRoles());
    }
}
