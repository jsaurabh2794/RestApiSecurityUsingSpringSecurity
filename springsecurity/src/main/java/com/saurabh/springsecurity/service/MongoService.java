package com.saurabh.springsecurity.service;

import com.saurabh.springsecurity.collection.ApplicationUser;

public interface MongoService {

    public void registerUser(ApplicationUser user);

    public ApplicationUser findUserByUserName(String username);
}
