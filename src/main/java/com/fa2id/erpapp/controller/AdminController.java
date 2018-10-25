package com.fa2id.erpapp.controller;

import com.fa2id.erpapp.model.Order;
import com.fa2id.erpapp.model.User;
import com.fa2id.erpapp.service.UserService;
import com.fa2id.erpapp.utils.MyJsonProtocol;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final MyJsonProtocol myJsonProtocol;

    @Autowired
    public AdminController(UserService userService, ObjectMapper objectMapper, MyJsonProtocol myJsonProtocol) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.myJsonProtocol = myJsonProtocol;
    }

    @RequestMapping(value = {"/", "/panel"}, method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin-panel");
        return modelAndView;
    }


    @RequestMapping(value = "/panel/register", method = RequestMethod.POST)
    @ResponseBody
    public ObjectNode processRegistrationForm(User user, @RequestParam String role) {
        String message;
        int status = 200;
        ObjectNode resultNode = objectMapper.createObjectNode();
        User existedUser = userService.findByUsername(user.getUsername());
        if (existedUser == null) {
            User registeredUser = userService.registerUser(user, role);
            message = "User registered successfully. \nUser ID: "
                    + registeredUser.getId() + " \nUsername: "
                    + registeredUser.getUsername();
        } else
            message = "User already existed.";
        return myJsonProtocol.createResponseObjectResult(status, message, resultNode);
    }

    @RequestMapping(
            value = "/users/v1/get/all",
            method = RequestMethod.GET)
    @ResponseBody
    public ObjectNode getAllUsers() {
        String message;
        int status = 200;
        List<User> users = userService.getAllUsers();
        List<ObjectNode> userNodes = new ArrayList<>();
        myJsonProtocol.makeUserArray(users, userNodes);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.addAll(userNodes);
        if (users.isEmpty())
            message = "No users found.";
        else
            message = "Got all users.";
        return myJsonProtocol.createResponseArrayResult(status, message, arrayNode);
    }
}
