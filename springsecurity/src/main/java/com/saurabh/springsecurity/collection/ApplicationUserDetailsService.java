package com.saurabh.springsecurity.collection;

import com.saurabh.springsecurity.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("applicationUserDetailsService")
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private MongoService mongoService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        ApplicationUser user = null;
        try {
            user = mongoService.findUserByUserName(s);
        } catch (Exception ex) {
            throw new UsernameNotFoundException(String.format("Username %s not registered with us", s));
        }
        return user;
    }
}
