package com.saurabh.springsecurity.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component("apiResponse")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponse {
    private int status;
    private String message;
    private Object result;
}
