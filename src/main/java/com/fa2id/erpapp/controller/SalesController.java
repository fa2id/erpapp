package com.fa2id.erpapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sales")
public class SalesController {

    @RequestMapping(value = "/panel")
    public String getPanel(){
        return "sales-panel";
    }
}
