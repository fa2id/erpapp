package com.fa2id.erpapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView getPage() {
        System.out.println("GETPAGE");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = "/login/default", method = RequestMethod.GET)
    public ModelAndView getPageAfterLogin() {
        System.out.println("RUNNING");
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(System.out::println);
        for (GrantedAuthority s : authentication.getAuthorities()) {
            switch (s.getAuthority()) {
                case "SALES":
                    modelAndView.setViewName("redirect:/sales/panel");
                    break;
                case "SCM":
                    modelAndView.setViewName("redirect:/scm/panel");
                    break;
                case "ADMIN":
                    modelAndView.setViewName("redirect:/admin/panel");
                    break;
                default:
                    modelAndView.setViewName("redirect:/access-denied");
                    break;
            }
        }
        return modelAndView;
    }

}
