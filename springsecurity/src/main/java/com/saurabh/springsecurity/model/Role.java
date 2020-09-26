package com.saurabh.springsecurity.model;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),STUDENT("STUDENT");

    private String message;
    Role(String message) {
        this.message = message;
    }
}
