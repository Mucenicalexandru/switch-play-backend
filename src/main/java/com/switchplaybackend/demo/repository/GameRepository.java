package com.switchplaybackend.demo.repository;

import com.switchplaybackend.demo.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

}
