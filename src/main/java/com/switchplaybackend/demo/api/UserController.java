package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.Offer;
import com.switchplaybackend.demo.model.User;
import com.switchplaybackend.demo.repository.OfferRepository;
import com.switchplaybackend.demo.repository.UserRepository;
import com.switchplaybackend.demo.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenServices jwtTokenServices;

    public UserController(AuthenticationManager authenticationManager,
                          JwtTokenServices jwtTokenServices,
                          UserRepository userRepository){
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;


    @GetMapping("/users")
    public List<User> getAllUsers(){ return userRepository.findAll();}


    @GetMapping("/get-user-by-id/{id}")
    public Optional<User> getUserByID(@PathVariable UUID id){
        return userRepository.findById(id);
    }

    @GetMapping("/get-active-offers-by-user-id/{id}")
    public List<Offer> getActiveOffersByUser(@PathVariable UUID id){
        return offerRepository.getAllByUser_Id(id);
    }


    @PostMapping("/add-user")
    public ResponseEntity<?> addUser (@Valid @RequestBody User user) throws URISyntaxException {

       if(userRepository.existsByEmail(user.getEmail())){
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<>(responseHeaders, HttpStatus.CONFLICT);
       }else{
           String password = passwordEncoder.encode(user.getPassword());
           System.out.println(password.getBytes());
           user.setPassword(password);

           User result = userRepository.save(user);
           return ResponseEntity.created(new URI("/add-user" + result.getId())).body(result);
       }

    }


    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser, @PathVariable UUID id) throws URISyntaxException {
        User userToBeUpdated = userRepository.findById(id).get();
        userToBeUpdated.setFirstName(updatedUser.getFirstName());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setEmail(updatedUser.getEmail());
        userToBeUpdated.setCountry(updatedUser.getCountry());
        userToBeUpdated.setTown(updatedUser.getTown());
        userToBeUpdated.setPhone(updatedUser.getPhone());
        userToBeUpdated.setConsole(updatedUser.getConsole());
        User updated = userRepository.save(userToBeUpdated);
        return ResponseEntity.created(new URI("/update-user" + updated.getId())).body(updated);

    }

}
