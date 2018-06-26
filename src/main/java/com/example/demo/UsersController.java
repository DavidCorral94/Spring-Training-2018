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
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(basePath + "/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        User user = null;
        int i;
        for (i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getId() == id) {
                user = u;
                break;
            }
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(basePath)
    public ResponseEntity<?> newUser(@RequestParam("name") String name, @RequestParam("age") int age,
                                     @RequestParam("location") String location) {
        User u = new User(ids.getAndIncrement(), name, age, location);
        users.add(u);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PutMapping(basePath + "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestParam("name") String name,
                                        @RequestParam("age") int age, @RequestParam("location") String location) {
        User user = null;
        int i;
        for (i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getId() == id) {
                user = u;
                break;
            }
        }

        if (user != null) {
            user.setName(name);
            user.setAge(age);
            user.setLocation(location);
            users.set(i, user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no User with that ID", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(basePath + "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        int i;
        for (i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id)
                break;
        }
        users.remove(i);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
