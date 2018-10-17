package com.fa2id.erpapp.controller;

import com.fa2id.erpapp.model.User;
import com.fa2id.erpapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "/panel"}, method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin-panel");
        return modelAndView;
    }


    @RequestMapping(value = "/panel/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(User user, @RequestParam String role) {
        ModelAndView modelAndView = new ModelAndView();
        User existedUser = userService.findByUsername(user.getUsername());
        if (existedUser == null)
            userService.registerUser(user, role);
        modelAndView.setViewName("admin-panel");
        return modelAndView;
    }
}
