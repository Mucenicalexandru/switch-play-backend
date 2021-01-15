package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.User;
import com.switchplaybackend.demo.repository.OfferRepository;
import com.switchplaybackend.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/users")
    public List<User> getAllUsers(){ return userRepository.findAll();}


    @GetMapping("/get-user-by-id/{id}")
    public Optional<User> getUserByID(@PathVariable UUID id){
        return userRepository.findById(id);

    }


    @PostMapping("/add-user")
    public ResponseEntity<?> addUser (@Valid @RequestBody User user) throws URISyntaxException {

       if(userRepository.existsByEmail(user.getEmail())){
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<>(responseHeaders, HttpStatus.CONFLICT);
       }else{
           String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
           user.setPassword(password);

           User result = userRepository.save(user);
           return ResponseEntity.created(new URI("/add-user" + result.getId())).body(result);
       }

    }
    @PostMapping("/check-if-user")
    public ResponseEntity<?> checkUser(@Valid @RequestBody User user) throws URISyntaxException{
        if(userRepository.existsByEmail(user.getEmail())){
            if(BCrypt.checkpw(user.getPassword(), userRepository.findByEmail(user.getEmail()).getPassword())){
                HttpHeaders responseHeaders = new HttpHeaders();
                System.out.println("correct password");
                return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
            }else{
                System.out.println("wrong password");
                HttpHeaders responseHeaders = new HttpHeaders();
                return new ResponseEntity<>(responseHeaders, HttpStatus.UNAUTHORIZED);
            }
        }else{
            System.out.println("wrong email");
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<>(responseHeaders, HttpStatus.CONFLICT);
        }

    }

}
