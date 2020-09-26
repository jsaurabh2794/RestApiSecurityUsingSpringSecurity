package com.saurabh.springsecurity.repository;

import com.saurabh.springsecurity.collection.ApplicationUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mongodbRepository")
public interface MongoDbRepo extends MongoRepository<ApplicationUser, Integer> {

    public ApplicationUser findByUserName(String userName);

    public ApplicationUser findById(int id);
}
