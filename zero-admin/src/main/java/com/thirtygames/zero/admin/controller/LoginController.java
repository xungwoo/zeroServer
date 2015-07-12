package com.thirtygames.zero.admin.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class LoginController {
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login()
    {
    	log.debug("Login Controller::");
        return "login";
    }
    
    @RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
    public String accessDenied(Model model) 
    {
        model.addAttribute("message", "Permission denied - please login");
        return "accessdenied";
    }  
}
