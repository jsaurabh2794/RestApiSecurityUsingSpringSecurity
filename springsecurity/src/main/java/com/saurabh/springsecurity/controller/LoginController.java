package com.saurabh.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("CustomLogin")
    public String loginPage(){
        return "CustomLogin";
    }

    @RequestMapping("home")
    public String goToHome(){
        return "Home";
    }

}
