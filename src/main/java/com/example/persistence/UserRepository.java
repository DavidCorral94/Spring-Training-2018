package com.example.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {
    public List<User> findByName(String name);
    public List<User> findById(long id);
    public long removeById(long id);
}