package com.crimson.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Meow on 18.03.2017.
 */
@Controller
@RequestMapping("/error")
public class HttpErrorHandler {

    @RequestMapping("/404")
    public String error404() {
        return "error404";
    }

    @RequestMapping("/500")
    public String error500() {
        return "error500";
    }
    @RequestMapping("/401")
    public String error401() {
        return "error401";
    }
}
