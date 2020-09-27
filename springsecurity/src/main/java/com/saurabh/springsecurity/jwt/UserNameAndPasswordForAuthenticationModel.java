package com.saurabh.springsecurity.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNameAndPasswordForAuthenticationModel {
    private String username;
    private String password;
}
