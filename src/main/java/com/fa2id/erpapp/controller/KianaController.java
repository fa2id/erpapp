package com.fa2id.erpapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fa2id
 * Date: 30/01/19
 * Time: 2:00 PM
 * Web: www.fa2id.com
 */
@Controller
public class KianaController {

    @RequestMapping(value = "/kianajavid")
    public ModelAndView getKianaJavid(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("kianajavid");
        return modelAndView;
    }
}
