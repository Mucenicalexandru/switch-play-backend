package com.switchplaybackend.demo.model.messages;

import com.switchplaybackend.demo.model.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "inbox", catalog = "switch_play")
@Data
public class Inbox {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID InboxId;

    @OneToMany( cascade = CascadeType.ALL)
    private List<Message> receivedMessages;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> sentMessages;

    @JoinColumn(name="user_id", referencedColumnName = "id")
    private UUID userId;


    public UUID getUser() { return userId; }

    public void setUser(UUID userId) { this.userId = userId;}
    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void addSentMessages(Message message) {
        sentMessages.add(message);
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void addReceivedMessages(Message message) {
        receivedMessages.add(message);
    }



}
