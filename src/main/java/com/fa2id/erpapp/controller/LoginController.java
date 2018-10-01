package com.fa2id.erpapp.controller;

import com.fa2id.erpapp.Service.UserService;
import com.fa2id.erpapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/")
    public String getHome(){
        User user = new User();
        user.setFirstName("farid");
        userService.saveUser(user);
        System.out.println("MAYBE USER SAVED..!");
        return "login";
    }

}
