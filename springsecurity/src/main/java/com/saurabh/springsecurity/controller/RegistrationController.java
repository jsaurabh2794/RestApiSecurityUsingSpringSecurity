package com.saurabh.springsecurity.controller;

import com.saurabh.springsecurity.collection.CustomUserDetails;
import com.saurabh.springsecurity.model.UserDetailsForSignUp;
import com.saurabh.springsecurity.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class RegistrationController {

    @Autowired
    private MongoService mongoService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public String doRegister(@RequestBody UserDetailsForSignUp user) {
        Random random = new Random();
        String id = String.valueOf(random.nextInt(10000));
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        user.getRole().stream().forEach(role -> grantedAuthorityList.add(new SimpleGrantedAuthority(role.getMessage())));
        CustomUserDetails customUserDetails = new CustomUserDetails(
                id,
                user.getUserName(),
                passwordEncoder.encode(user.getPassword()),
                grantedAuthorityList,
                true,
                true,
                true,
                true);
        mongoService.registerUser(customUserDetails);
        return "Successfully registered!!";
    }
}
