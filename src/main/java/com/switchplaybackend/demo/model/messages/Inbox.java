package com.switchplaybackend.demo.model.messages;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "inbox", catalog = "switch_play")
public class Inbox {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID InboxId;

    @OneToMany(mappedBy = "InboxId")
    private List<Message> messageList;


}
