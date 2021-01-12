package com.switchplaybackend.demo.repository;

import com.switchplaybackend.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository  extends JpaRepository<User, UUID> {

}
