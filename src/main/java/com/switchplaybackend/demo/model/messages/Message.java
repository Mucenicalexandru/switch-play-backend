package com.switchplaybackend.demo.model.messages;

import com.switchplaybackend.demo.model.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "messages", catalog = "switch_play")
@Data
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


    private String message;


    private UUID senderId;


    private UUID receiverId;



}
