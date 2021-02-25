package com.switchplaybackend.demo.repository;

import com.switchplaybackend.demo.model.User;
import com.switchplaybackend.demo.model.messages.Inbox;
import com.switchplaybackend.demo.model.messages.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InboxRepository  extends JpaRepository<Inbox, UUID> {
    Inbox getByUserId(UUID user_id);
}