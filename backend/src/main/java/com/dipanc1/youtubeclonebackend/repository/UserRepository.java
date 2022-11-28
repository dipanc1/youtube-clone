package com.dipanc1.youtubeclonebackend.repository;

import com.dipanc1.youtubeclonebackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findBySub(String sub);
}
