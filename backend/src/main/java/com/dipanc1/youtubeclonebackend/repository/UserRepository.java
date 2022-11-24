package com.dipanc1.youtubeclonebackend.repository;

import com.dipanc1.youtubeclonebackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
