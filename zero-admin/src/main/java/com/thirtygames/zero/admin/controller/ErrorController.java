package com.thirtygames.zero.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thirtygames.zero.admin.controller.common.AdminBaseController;

@Controller
public class ErrorController extends AdminBaseController {
	
	
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error()
    {
        return "error";
    }

}