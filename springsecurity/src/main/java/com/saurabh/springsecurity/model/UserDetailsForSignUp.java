package com.saurabh.springsecurity.model;

import com.saurabh.springsecurity.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDetailsForSignUp {
    private String userName;
    private String password;
    private List<Role> role;
}
