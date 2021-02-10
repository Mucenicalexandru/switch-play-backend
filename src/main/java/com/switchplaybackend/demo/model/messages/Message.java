package com.switchplaybackend.demo.model.messages;

import com.switchplaybackend.demo.model.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "message", catalog = "switch_play")
public class Message {
    public Message() {

    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "InboxId",nullable = false)
    private Inbox InboxId;

    private String message;

    @OneToOne
    private User sender;

    @OneToOne
    private User receiver;



}
