package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.User;
import com.switchplaybackend.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserControllerTest {

    @MockBean
    private UserRepository repository;

    @Autowired
    private UserController userController;

    private final User user1 = new User();
    private final User user2 = new User();

    @BeforeEach
    void setup(){
        user1.setFirstName("First Name");
        user1.setLastName("Last Name");
        user1.setConsole("Playstation");
        user1.setCountry("Romania");
        user1.setTown("Bucharest");

        user2.setFirstName("First Name");
        user2.setLastName("Last Name");
        user2.setConsole("Xbox");
        user2.setCountry("Romania");
        user2.setTown("Constanta");
    }

    @Test
    public void getAllUsersTest(){
        when(repository.findAll()).thenReturn(Stream
                .of(user1, user2).collect(Collectors.toList()));

        assertEquals(2, userController.getAllUsers().size());
    }

    @Test
    public void getUserByIdTest(){
        UUID id = UUID.randomUUID();
        user2.setId(id);

        when(repository.findById(id)).thenReturn(java.util.Optional.of(user2));

        assertEquals(user2, userController.getUserByID(id).get());
    }

    @Test
    public void addUserTest() throws URISyntaxException {
        user1.setPassword("password");
        when(repository.save(user1)).thenReturn(user1);

        assertEquals(user1, userController.addUser(user1).getBody());
    }


}
