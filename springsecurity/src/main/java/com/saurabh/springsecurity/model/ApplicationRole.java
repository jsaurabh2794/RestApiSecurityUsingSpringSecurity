package com.saurabh.springsecurity.model;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ApplicationRole {
    ADMIN("ADMIN"),
    STUDENT("STUDENT");

    private String msg;

    ApplicationRole(String msg) {
        this.msg = msg;
    }
}
