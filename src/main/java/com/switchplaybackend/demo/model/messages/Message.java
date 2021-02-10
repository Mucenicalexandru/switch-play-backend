package com.switchplaybackend.demo.model.messages;

import com.switchplaybackend.demo.model.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "messages", catalog = "switch_play")
public class Message {
    public Message() {

    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID message_id;

    @ManyToOne
    @JoinColumn(name = "inbox_id",nullable = false)
    private Inbox InboxId;

    private String message;

    @OneToOne
    private User sender;

    @OneToOne
    private User receiver;



}
