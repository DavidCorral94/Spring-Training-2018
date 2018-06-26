package com.example.persistence;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UsersController {

    @Autowired
    UserRepository userRepository;

    private final String basePath = "/users";

    @GetMapping(basePath)
    public ResponseEntity<?> getUsers() {
        System.out.println("/GET getUsers - Returning all users");
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(basePath + "/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        User u = userRepository.findById(id).get(0);
        System.out.println("/GET getUser - Returning one user: " + u);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping(basePath)
    public ResponseEntity<?> newUser(@RequestParam("id") long id, @RequestParam("name") String name,
                                     @RequestParam("age") int age, @RequestParam("location") String location) {
        User u = new User(id, name, age, location);
        userRepository.save(u);
        System.out.println("/POST newUser - Creating new user: " + u);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PutMapping(basePath + "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestParam("name") String name,
                                        @RequestParam("age") int age, @RequestParam("location") String location) {
        User u = userRepository.findById(id).get(0);
        u.setName(name);
        u.setAge(age);
        u.setLocation(location);
        userRepository.removeById(id);
        System.out.println("/UPDATE updateUser - Updating user: " + u);
        userRepository.save(u);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @DeleteMapping(basePath + "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        long u = userRepository.removeById(id);
        System.out.println("/DELETE deleteUser - Deleting user with id " + u);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

}
