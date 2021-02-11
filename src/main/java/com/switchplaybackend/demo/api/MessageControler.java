package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.messages.Inbox;
import com.switchplaybackend.demo.model.messages.Message;
import com.switchplaybackend.demo.repository.InboxRepository;
import com.switchplaybackend.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.UUID;

@RestController
@Transactional
@RequestMapping("/api")
public class MessageControler {

    @Autowired
    private InboxRepository inboxRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping(path="/send-message")
    public ResponseEntity<?> send_message(@RequestBody Message message) throws URISyntaxException {

        Inbox receiver_inbox = inboxRepository.getByUserId(message.getSenderId());
        Inbox sender_inbox = inboxRepository.getByUserId(message.getReceiverId());

        message.setSenderUserName(userRepository.getOne(message.getSenderId()).getFirstName());
        message.setReceiverUserName(userRepository.getOne(message.getReceiverId()).getFirstName());

        receiver_inbox.addReceivedMessages(message);

        sender_inbox.addSentMessages(message);
        java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        message.setDate(currentDate);

        Inbox result = inboxRepository.save(receiver_inbox);
        inboxRepository.save(sender_inbox);

        return ResponseEntity.created(new URI("/send-message" + result.getInboxId())).body(result);

    }
    @GetMapping(path = "/get-inbox/{userID}")
    public Inbox  getInboxByUserId(@PathVariable UUID userID){
        System.out.println(inboxRepository.getByUserId(userID));
        return inboxRepository.getByUserId(userID);
    }
}
