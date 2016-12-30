package com.crimson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/tv")
@Controller
public class CrimsonController {

    @GetMapping("/gameofthrones")
    public String displayN(Model model) {
        model.addAttribute("title", "Game of Thrones");
        return "tvShow";
    }


}
