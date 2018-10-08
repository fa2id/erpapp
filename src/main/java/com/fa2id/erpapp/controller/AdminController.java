package com.fa2id.erpapp.controller;

import com.fa2id.erpapp.model.User;
import com.fa2id.erpapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Autowired
    public AdminController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @RequestMapping(value = "/panel", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin-panel");
        return modelAndView;
    }


    @RequestMapping(value = "/panel", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user,
                                                BindingResult bindingResult, @RequestParam String role) {

        User userExists = userService.findByUsername(user.getUsername());

        if (userExists != null) {
            modelAndView.setViewName("admin-panel");
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin-panel");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.saveUser(user, role);
            modelAndView.setViewName("admin-panel");
        }

        return modelAndView;
    }
}
