package com.crimson.controller;

import com.crimson.dto.UserDTO;
import com.crimson.model.User;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/tv")
@Controller
public class CrimsonController {

    @Autowired
    MapperFacade mapperFacade;


    @GetMapping("/gameofthrones")
    public String displayN(Model model) {
        model.addAttribute("title", "Game of Thrones");
        return "tvShow";
    }

    @GetMapping("/genre/{name}")
    public String displayGenre(@PathVariable String name, Model model) {
        model.addAttribute("genre", name);
        model.addAttribute("title", "Game of Thrones");
        return "genreList";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchResult(Model model, HttpServletRequest request) {
        model.addAttribute("search", request.getParameter("search"));
        return "searchResult";
    }

    @GetMapping("/map")
    @ResponseBody
    public Object mapThis(Model model) {
        User user = new User();
        user.setName("Kamil Kot");
        user.setEmail("kaamil.kot@gmail.com");
        user.setPassword("casdsadasd");
        UserDTO userDto = mapperFacade.map(user, UserDTO.class);
        System.out.print(userDto);
        return userDto;
    }

}
