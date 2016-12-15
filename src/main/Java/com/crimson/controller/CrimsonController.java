package com.crimson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/tv")
@Controller
public class CrimsonController {

    @GetMapping("/{name}")
    public String displayTvShow(@PathVariable String name, Model model) {
        model.addAttribute("title", name);
        return "crimson";
    }
}
