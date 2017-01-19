package com.crimson.controller;

import com.crimson.dto.TvShowDTO;
import com.crimson.dto.UserDTO;
import com.crimson.service.GenreService;
import com.crimson.service.RatingService;
import com.crimson.service.TvShowService;
import com.crimson.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/tv")
@Controller
public class CrimsonController {

    @Autowired
    private TvShowService tvShowService;
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private GenreService genreService;

    @Transactional
    @GetMapping("/{name}")
    public String displayTvShow(Model model, @PathVariable("name") String name) {
        TvShowDTO tv = tvShowService.getTvBySlug(name);
        boolean follow = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDTO user = userService.getUserByName(auth.getName());
            follow = userService.checkFollow(user, tv);
        }
        model.addAttribute("tv", tv);
        model.addAttribute("follow", follow);
        return "tvShow";
    }

    @Transactional(readOnly = true)
    @GetMapping("/user/{name}")
    public String displayUser(Model model, @PathVariable("name") String name) {
        UserDTO user = userService.getUserByName(name);
        List<TvShowDTO> tvs = userService.getUserTvShows(user);
        model.addAttribute("tvshows", tvs);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/genre/{name}")
    public String displayGenre(@PathVariable String name, Model model) {
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        model.addAttribute("genre", name);
        model.addAttribute("tvshows", tvShowService.getTvByGenre(name));
        return "tvShowList";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchResult(Model model, HttpServletRequest request) {
        model.addAttribute("search", request.getParameter("search"));
        return "searchResult";
    }

    @GetMapping("/list")
    public String tvShowList(Model model) {
        model.addAttribute("tvshows", tvShowService.getAllTvShows());
        return "tvShowList";
    }

    @Transactional
    @RequestMapping(value = "/follow/{id}")
    public String follow(Model model, @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        TvShowDTO tv = tvShowService.getTvById(id);

        if (userService.checkFollow(user,tv)) userService.deleteTvShowFromUser(user,tv);
        else userService.addTvShow2User(user, tv);

        return "redirect:/tv/" + tv.getSlug();
    }

    @Transactional
    @RequestMapping(value = "/rate/{id}")
    public String rate(Model model, @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        TvShowDTO tv = tvShowService.getTvById(id);
        ratingService.saveUserRating(user, tv, 7);

        return "redirect:/tv/" + tv.getSlug();
    }

}
