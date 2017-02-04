package com.crimson.mvc.controller;

import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.service.EpisodeService;
import com.crimson.core.service.RatingService;
import com.crimson.core.service.TvShowService;
import com.crimson.core.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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
    private EpisodeService episodeService;


    @GetMapping("/{name}")
    public String displayTvShow(Model model, @PathVariable("name") String name) {
        TvShowDTO tv = tvShowService.getTvBySlug(name);
        boolean follow = false;
        int rating = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDTO user = userService.getUserByName(auth.getName());
            follow = userService.checkFollow(user, tv);
            rating = ratingService.getRating(tv.getId(), user.getId()).getValue();

        }
        int seasons = 0;
        for (Episode episode: tv.getEpisodes()) {
            if (seasons < episode.getSeason()) seasons = episode.getSeason();
        }
        model.addAttribute("tv", tv);
        model.addAttribute("episodes", tv.getEpisodes());
        model.addAttribute("seasons", seasons);
        model.addAttribute("rating", rating);
        model.addAttribute("follow", follow);
        return "tvShow";
    }


    //TODO metoda która zwraca nieobejrzane odcinki uzytkownika/nadchodzace odcinki
    //TODO getUserEpisodeList() nie dziala dobrze
    @GetMapping("/user/{name}")
    public String displayUser(Model model, @PathVariable("name") String name) {
        UserDTO user = userService.getUserByName(name);
        List<TvShowDTO> tvs = userService.getUserTvShows(user);
        //userService.getUserUpcomingEpisodes(user);
        model.addAttribute("tvshows", tvs);
        model.addAttribute("watchedEpisodes", Lists.reverse(user.getUserEpisodeList()));
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/user/edit")
    @Secured("ROLE_USER")
    public String editUser(@RequestParam(value = "error", required = false) String error, Model model) {

        if (error != null) {
            model.addAttribute("error", "Error!");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDTO userDTO = userService.getUserByName(auth.getName());
            model.addAttribute("userDTO", userDTO);
            return "userEdit";
        } else return "redirect:/";
    }

    //TODO głupio zrobione, poprawić
    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String registration(@Valid UserDTO userDTO, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return String.format("redirect:/tv/user/%s/edit?error", userDTO.getName());
        }
        userService.updateUser(userDTO);
        return String.format("redirect:/tv/user/%s", userDTO.getName());
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


    @RequestMapping(value = "/follow/{id}")
    public String follow(Model model, @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        TvShowDTO tv = tvShowService.getTvById(id);

        if (userService.checkFollow(user, tv)) userService.deleteTvShowFromUser(user, tv);
        else userService.addTvShow2User(user, tv);

        return String.format("redirect:/tv/%s", tv.getSlug());
    }




    @RequestMapping(value = "/rate", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void rate(@RequestParam("id") long id, @RequestParam("value") int value) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        TvShowDTO tv = tvShowService.getTvById(id);
        ratingService.saveUserRating(user, tv, value);
    }

    @RequestMapping(value = "/watched", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void watched(@RequestParam("id") long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        Episode episode = episodeService.getEpisodeById(id);
        if (episodeService.checkWatched(user, episode)) episodeService.deleteUserFromEpisode(user, episode);
        else episodeService.addUser2Episode(user, episode);
    }
}
