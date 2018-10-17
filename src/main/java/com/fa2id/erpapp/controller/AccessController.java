package com.fa2id.erpapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessController {

    @RequestMapping(value = "/access-denied")
    public ModelAndView getDeniedAccess() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("access-denied");
        return modelAndView;
    }
}
