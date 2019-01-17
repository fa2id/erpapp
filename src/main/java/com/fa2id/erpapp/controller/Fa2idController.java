package com.fa2id.erpapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Fa2idController {

    @RequestMapping(value = "/fa2id")
    public ModelAndView getFa2id(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fa2id");
        return modelAndView;
    }
}
