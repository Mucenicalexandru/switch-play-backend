package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.Game;
import com.switchplaybackend.demo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/games")
    public List<Game> getGames(){
        return gameRepository.findAll();
    }

    @PostMapping("/add-game")
    public ResponseEntity<Game> addGame(@Valid @RequestBody Game game) throws URISyntaxException {
        Game result = gameRepository.save(game);
        return ResponseEntity.created(new URI("/api/add-game" + result.getId())).body(result);
    }

    @DeleteMapping("/delete-game/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable UUID id){
        gameRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
