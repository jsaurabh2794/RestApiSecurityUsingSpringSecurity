package com.saurabh.springsecurity.service;

import com.saurabh.springsecurity.collection.ApplicationUser;
import com.saurabh.springsecurity.repository.MongoDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoServiceImpl implements MongoService {

    @Autowired
    private MongoDbRepo mongodbRepository;

    @Override
    public void registerUser(ApplicationUser user) {
        mongodbRepository.save(user);
    }

    @Override
    public ApplicationUser findUserByUserName(String username) {
        return mongodbRepository.findByUserName(username);
    }
}
