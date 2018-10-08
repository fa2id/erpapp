package com.fa2id.erpapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping(value = "/sales")
public class SalesController {

    @RequestMapping(value = "/panel")
    public ModelAndView getPanel() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sales-panel");
        return modelAndView;
    }

    @RequestMapping(
            value = {"/place", "/cancel", "/orders", "/shipment", "/invoice"},
            consumes = {"text/plain", "application/*"})
    @ResponseBody
    public Map<String, String> placeOrder(@RequestParam Map<String, String> body) {
        body.forEach((key, value) -> System.out.println(key + " " + value + " " + value.getClass().getTypeName()));
        return body;
    }
}
