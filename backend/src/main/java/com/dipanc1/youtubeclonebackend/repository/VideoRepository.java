package com.dipanc1.youtubeclonebackend.repository;

import com.dipanc1.youtubeclonebackend.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface VideoRepository extends MongoRepository<Video, String> { }
