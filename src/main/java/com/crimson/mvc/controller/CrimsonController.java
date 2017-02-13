package com.crimson.mvc.controller;

import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @SuppressWarnings("unchecked")
    public String displayTvShow(Model model, @PathVariable("name") String name) {
        TvShowDTO tv = tvShowService.getTvBySlug(name);
        boolean follow = false;
        int rating = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDTO user = userService.getUserByName(auth.getName());
            follow = userService.checkFollow(user, tv);
            rating = ratingService.getRating(tv.getId(), user.getId()).getValue();
            model.addAttribute("user", user);
            List watchedEpisodesId = new ArrayList();
            user.getUserEpisodeList().forEach(episode -> watchedEpisodesId.add(episode.getId()));
            model.addAttribute("watchedEpisodesId", watchedEpisodesId);
        }
        int seasons = 0;
        for (Episode episode : tv.getEpisodes()) {
            if (seasons < episode.getSeason()) seasons = episode.getSeason();
        }
        model.addAttribute("tv", tv);
        model.addAttribute("episodes", tv.getEpisodes());
        model.addAttribute("seasons", seasons);
        model.addAttribute("rating", rating);
        model.addAttribute("follow", follow);
        return "tvShow";
    }

    @GetMapping("/user/{name}")
    @SuppressWarnings("unchecked")
    public String displayUser(Model model, @PathVariable("name") String name) {
        UserDTO user = userService.getUserByName(name);
        List<TvShowDTO> tvs = userService.getUserTvShows(user);
        List<TvShow> favorites = userService.getUserTvShowsSortedByMaxRating(user);
        List<Episode> watchedEpisodes = user.getUserEpisodeList();
        List watchedEpisodesId = new ArrayList();
        watchedEpisodes.forEach(episode -> watchedEpisodesId.add(episode.getId()));
        model.addAttribute("tvshows", tvs);
        model.addAttribute("watchedEpisodes", Lists.reverse(watchedEpisodes));
        model.addAttribute("watchedEpisodesId", watchedEpisodesId);
        model.addAttribute("upcomimgEpisodes", userService.getAllUpcomingUserEpisodes(user));
        model.addAttribute("favorites", favorites);
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
    public String registration(@Valid UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/tv/user/edit?error";
        }
        userService.updateUser(userDTO);
        return String.format("redirect:/tv/user/%s", userDTO.getName());
    }

    //USUWANIE USERA
    @RequestMapping(value = "/user/delete/", method = RequestMethod.GET)
    @Secured("ROLE_USER")
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        userService.deleteUser(user);
        request.logout();
        response.sendRedirect("/");
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


    @RequestMapping(value = "/follow")
    @Secured("ROLE_USER")
    public String follow(@RequestParam("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        TvShowDTO tv = tvShowService.getTvById(id);

        if (userService.checkFollow(user, tv)) userService.deleteTvShowFromUser(user, tv);
        else userService.addTvShow2User(user, tv);

        return String.format("redirect:/tv/%s", tv.getSlug());
    }


    @RequestMapping(value = "/rate", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER")
    public void rate(@RequestParam("id") long id, @RequestParam("value") int value) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        TvShowDTO tv = tvShowService.getTvById(id);
        ratingService.saveUserRating(user, tv, value);

    }

    @RequestMapping(value = "/watched", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER")
    @Transactional
    public void watched(@RequestParam("id") long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        Episode episode = episodeService.getEpisodeById(id);
        if (episodeService.checkWatched(user, episode)) episodeService.deleteUserFromEpisode(user, episode);
        else episodeService.addUser2Episode(user, episode);
    }

    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    public String changeUserPassword(@RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("password") String password,
                                     @RequestParam("passwordConfirm") String passwordConfirm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        if (!userService.checkOldPassword(user, oldPassword)) return "redirect:/tv/user/updatePassword?wrongPassword";
        if (!Objects.equals(password, passwordConfirm)) return "redirect:/tv/user/updatePassword?mismatch";
        else userService.updatePassword(user, password);

        return String.format("redirect:/tv/user/%s", user.getName());
    }

    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.GET)
    public String displayChangeUserPassword(@RequestParam(value = "wrongPassword", required = false) String wrongPassword,
                                            @RequestParam(value = "mismatch", required = false) String mismatch, Model model) {
        if (mismatch != null) {
            model.addAttribute("error", "Password mismatch");
        }
        if (wrongPassword != null) {
            model.addAttribute("error", "Wrong password");
        }
        return "changePassword";
    }
}
