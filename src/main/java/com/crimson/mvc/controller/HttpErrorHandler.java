package com.crimson.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class HttpErrorHandler {

    //displays 404 error page
    @RequestMapping("/404")
    public String error404() {
        return "error404";
    }

    //displays 500 error page
    @RequestMapping("/500")
    public String error500() {
        return "error500";
    }

    //displays 401 error page
    @RequestMapping("/401")
    public String error401() {
        return "error401";
    }
}
