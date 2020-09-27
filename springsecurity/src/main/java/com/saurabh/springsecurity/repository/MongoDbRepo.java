package com.saurabh.springsecurity.repository;

import com.saurabh.springsecurity.collection.CustomUserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mongodbRepository")
public interface MongoDbRepo extends MongoRepository<CustomUserDetails, Integer> {

    public CustomUserDetails findByUserName(String userName);

    public CustomUserDetails findById(int id);
}
