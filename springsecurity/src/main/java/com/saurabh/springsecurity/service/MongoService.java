package com.saurabh.springsecurity.service;

import com.saurabh.springsecurity.collection.CustomUserDetails;

public interface MongoService {

    public void registerUser(CustomUserDetails user);

    public CustomUserDetails findUserByUserName(String username);
}
