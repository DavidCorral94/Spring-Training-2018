package com.example.demo;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UsersController {

    private static List<User> users = new ArrayList<>();
    private final String basePath = "/users";
    private static AtomicLong ids = new AtomicLong(0);

    /*@RequestMapping(value = basePath, method = RequestMethod.GET)
    @ResponseBody*/
    @GetMapping(basePath)
    public ResponseEntity<?> users() {
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(basePath)
    public ResponseEntity<?> newUser(@RequestParam("name") String name, @RequestParam("age") int age,
                                     @RequestParam("location") String location) {
        User user = new User(ids.getAndIncrement(), name, age, location);
        users.add(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
