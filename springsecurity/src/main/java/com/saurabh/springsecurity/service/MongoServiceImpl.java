package com.saurabh.springsecurity.service;

import com.saurabh.springsecurity.collection.CustomUserDetails;
import com.saurabh.springsecurity.repository.MongoDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoServiceImpl implements MongoService {

    @Autowired
    private MongoDbRepo mongodbRepository;

    @Override
    public void registerUser(CustomUserDetails user) {
        mongodbRepository.save(user);
    }

    @Override
    public CustomUserDetails findUserByUserName(String username) {
        return mongodbRepository.findByUserName(username);
    }
}
