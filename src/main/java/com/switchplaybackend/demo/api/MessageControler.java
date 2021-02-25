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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
        return inboxRepository.getByUserId(userID);
    }

    @PostMapping(path = "/delete-message/{messageId}/{userId}")
    public void deleteMessage(@PathVariable UUID messageId ,@PathVariable UUID userId){
        Inbox inbox=getInboxByUserId(userId);
        List<Message> temp_SentMessages= new ArrayList<>();
        List<Message> temp_receivedMessages= new ArrayList<>();

        for (Message message: inbox.getSentMessages()) {
            System.out.println(message.getMessage_id());
            System.out.println(messageId);
            if(!message.getMessage_id().equals(messageId)){
                temp_SentMessages.add(message);
            }
        }
        for (Message message: inbox.getReceivedMessages()){
            if(!message.getMessage_id().equals(messageId)){
                temp_receivedMessages.add(message);
            }
        }
        System.out.println(temp_SentMessages);
        System.out.println(temp_receivedMessages);
        inbox.setSentMessages(temp_SentMessages);
        inbox.setReceivedMessages(temp_receivedMessages);
        inboxRepository.save(inbox);

    }
}
