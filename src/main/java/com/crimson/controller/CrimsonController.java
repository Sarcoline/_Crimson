package com.crimson.controller;

import com.crimson.dao.TvShowDAO;
import com.crimson.dao.UserDAO;
import com.crimson.dto.UserDTO;
import com.crimson.model.TvShow;
import com.crimson.model.User;
import com.github.slugify.Slugify;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/tv")
@Controller
public class CrimsonController {

    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    TvShowDAO tvShowDAO;
    @Autowired
    UserDAO userDAO;

    @GetMapping("/{name}")
    public String displayN(Model model, @PathVariable("name") String name) {
        if (tvShowDAO.getAllTvShows().size() < 1) {
            TvShow tv = new TvShow();
            tv.setGenre("Fantasy");
            tv.setTitle("Game Of Thrones");
            tv.setNetwork("HBO");
            tv.setOverallRating(9.2);
            tv.setTrailerUrl("https://www.youtube.com/watch?v=EI0ib1NErqg");
            tv.setCountry("USA");
            tv.setReleaseYear(2011);
            TvShow tv1 = new TvShow();
            tv1.setGenre("Drama");
            tv1.setTitle("Dexter");
            tv1.setNetwork("HBO");
            tv1.setOverallRating(9.2);
            tv1.setTrailerUrl("https://www.youtube.com/watch?v=EI0ib1NErqg");
            tv1.setCountry("USA");
            tv1.setReleaseYear(2006);
            tvShowDAO.saveTvShow(tv);
            tvShowDAO.saveTvShow(tv1);
        }

        model.addAttribute("tv", tvShowDAO.getTvShowBySlug(name));
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

    @Transactional
    @RequestMapping(value = "/follow/{id}")
    public String follow(Model model, @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByName(auth.getName());
        TvShow tv = tvShowDAO.getTvById(id);
        if (user.getTvShows().contains(tv)) user.getTvShows().remove(tv);
        else user.getTvShows().add(tv);


        Slugify slg = new Slugify();
        return "redirect:/tv/" + slg.slugify(tv.getTitle());
    }

}
