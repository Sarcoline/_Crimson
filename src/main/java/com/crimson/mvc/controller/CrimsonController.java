package com.crimson.mvc.controller;

import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.service.EpisodeService;
import com.crimson.core.service.RatingService;
import com.crimson.core.service.TvShowService;
import com.crimson.core.service.UserService;
import com.github.slugify.Slugify;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
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
        for (EpisodeDTO episode : tv.getEpisodes()) {
            if (seasons < episode.getSeason()) seasons = episode.getSeason();
        }
        model.addAttribute("tv", tv);
        model.addAttribute("episodes", tv.getEpisodes());
        model.addAttribute("seasons", seasons);
        model.addAttribute("rating", rating);
        model.addAttribute("follow", follow);
        return "tvShow";
    }

    @GetMapping("/{name}/edit")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String displayEditTvShow(@RequestParam(value = "error", required = false) String error,
                                    @PathVariable("name") String name, Model model) {
        if (error != null) {
            model.addAttribute("error", "Error!");
        }
        TvShowDTO tv = tvShowService.getTvBySlug(name);
        model.addAttribute("tv", tv);
        return "tvShowEdit";
    }

    @RequestMapping(value = "/{name}/edit", method = RequestMethod.POST)
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String postEditTvShow(@Valid TvShowDTO tvShowDTO, BindingResult bindingResult, @PathVariable("name") String name) {
        if (bindingResult.hasErrors()) {
            return String.format("redirect:/tv/%s/edit?error", name);
        }
        Slugify slugify = new Slugify();
        tvShowService.updateTvShow(tvShowDTO);
        return String.format("redirect:/tv/%s", slugify.slugify(tvShowDTO.getTitle()));
    }

    @GetMapping(value = "/add")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String addTvShow(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Error!");
        }
        model.addAttribute("tv", new TvShowDTO());
        return "addTvShow";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String addTvShow(@Valid TvShowDTO tvShowDTO, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "redirect:/tv/add?error";
        }
        tvShowService.saveTvShowDTO(tvShowDTO);
        return "redirect:/";
    }

    @GetMapping(value = "/{name}/delete")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String deleteTvShow(@PathVariable("name") String name) {
        tvShowService.deleteTvShow(tvShowService.getTvBySlug(name));
        return "redirect:/";
    }

    @RequestMapping(value = "/{name}/updatePicture", method = RequestMethod.POST)
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String postUpdatePicture(@PathVariable("name") String name,
                                    @RequestParam("key") String key,
                                    @RequestParam("pic1") MultipartFile pic1) throws IOException {
        tvShowService.updateTvShowPicture(name, key, pic1);
        return String.format("redirect:/tv/%s/edit", name);
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
        List tvs = tvShowService.getAllTvShows();
        model.addAttribute("tvshows", tvs);
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
    public void watched(@RequestParam("id") long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        EpisodeDTO episode = episodeService.getEpisodeById(id);
        if (episodeService.checkWatched(user, episode)) episodeService.deleteUserFromEpisode(user, episode);
        else episodeService.addUser2Episode(user, episode);
    }
}
