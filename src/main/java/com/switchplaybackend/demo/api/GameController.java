package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.Category;
import com.switchplaybackend.demo.model.Game;
import com.switchplaybackend.demo.repository.CategoryRepository;
import com.switchplaybackend.demo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/games")
    public List<Game> getGames(){
        return gameRepository.findAll();
    }


    @PostMapping("/add-game/{categoryId}")
    public ResponseEntity<Game> addGame(@PathVariable UUID categoryId, @RequestBody Game game) throws URISyntaxException {
        Category category = categoryRepository.findById(categoryId).get();
        game.setCategory(category);

        Game result = gameRepository.save(game);
        return ResponseEntity.created(new URI("/add-game" + result.getId())).body(result);
    }

    @DeleteMapping("/delete-game/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable UUID id){
        gameRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
