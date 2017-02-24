package com.crimson.mvc.controller;

import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.EpisodeFormDTO;
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
import java.util.Comparator;
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
        List<EpisodeDTO> episodes = tv.getEpisodes();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDTO user = userService.getUserByName(auth.getName());
            follow = userService.checkFollow(user, tv);
            rating = ratingService.getRating(tv.getId(), user.getId()).getValue();
            model.addAttribute("user", user);
            List watchedEpisodesId = new ArrayList();
            user.getEpisodes().forEach(episode -> watchedEpisodesId.add(episode.getId()));
            model.addAttribute("watchedEpisodesId", watchedEpisodesId);
        }
        int seasons = 0;
        for (EpisodeDTO episode : episodes) {
            if (seasons < episode.getSeason()) seasons = episode.getSeason();
        }
        episodes.sort(Comparator.comparing(EpisodeDTO::getSeason));
        episodes.sort(Comparator.comparing(EpisodeDTO::getNumber));
        model.addAttribute("tv", tv);
        model.addAttribute("episodes", episodes);
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
    public String postEditTvShow(@ModelAttribute("tv") @Valid TvShowDTO tvShowDTO, BindingResult bindingResult, @PathVariable("name") String name) {
        if (bindingResult.hasErrors()) {
            return "tvShowEdit";
        }
        Slugify slugify = new Slugify();
        tvShowService.updateTvShow(tvShowDTO);
        return String.format("redirect:/tv/%s", slugify.slugify(tvShowDTO.getTitle()));
    }

    @GetMapping("/{name}/edit/episodes")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String displayTvShowEpisodes(@RequestParam(value = "error", required = false) String error,
                                    @PathVariable("name") String name, Model model) {
        TvShowDTO tv = tvShowService.getTvBySlug(name);
        List<EpisodeDTO> episodes = tv.getEpisodes();
        episodes.sort(Comparator.comparing(EpisodeDTO::getSeason));
        episodes.sort(Comparator.comparing(EpisodeDTO::getNumber));
        if (error != null) {
            model.addAttribute("error", "Error!");
        }
        int seasons = 0;
        for (EpisodeDTO episode : episodes) {
            if (seasons < episode.getSeason()) seasons = episode.getSeason();
        }
        model.addAttribute("seasons", seasons);
        model.addAttribute("episodes", episodes);
        return "tvShowEpisodes";
    }

    @GetMapping("/{name}/edit/episodes/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String displayEditTvShowEpisodes(@RequestParam(value = "error", required = false) String error,
                                            @PathVariable("id") long id, Model model) {

        EpisodeFormDTO episodeFormDTO = episodeService.getEisodeForm(id);
        if (error != null) {
            model.addAttribute("error", "Error!");
        }
        model.addAttribute("episode", episodeFormDTO);
        return "tvShowEditEpisode";
    }

    @GetMapping("/{name}/edit/episodes/{id}/delete")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String deleteEpisode(@PathVariable("id") long id, @PathVariable("name") String name) {
        EpisodeDTO ep = episodeService.getEpisodeById(id);
        episodeService.deleteEpisode(ep);
        return String.format("redirect:/tv/%s/edit/episodes", name);
    }

    @GetMapping("/{name}/edit/episodes/add")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String displayAddTvShowEpisodes(@RequestParam(value = "error", required = false) String error,
                                           @PathVariable("name") String name, Model model) {

        TvShowDTO tv = tvShowService.getTvBySlug(name);
        long id = tv.getId();
        String title = tv.getTitle();
        String slug = tv.getSlug();
        if (error != null) {
            model.addAttribute("error", "Error!");
        }
        model.addAttribute("episode", new EpisodeFormDTO());
        model.addAttribute("idTvShow", id);
        model.addAttribute("title", title);
        model.addAttribute("slug", slug);
        return "addEpisode";
    }

    @RequestMapping(value = "/{name}/edit/episodes/add", method = RequestMethod.POST)
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String postAddTvShowEpisode(@ModelAttribute("episode") @Valid EpisodeFormDTO episodeFormDTO,
                                       BindingResult bindingResult, @PathVariable("name") String name) {
        if (bindingResult.hasErrors()) {
            return "addEpisode";
        }
        episodeService.addEpisodeFromForm(episodeFormDTO);
        return String.format("redirect:/tv/%s/edit/episodes", name);
    }


    @RequestMapping(value = "/{name}/edit/episodes/{id}", method = RequestMethod.POST)
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String postEditTvShowEpisodes(@ModelAttribute("episode") @Valid EpisodeFormDTO episodeFormDTO, BindingResult bindingResult,
                                         @PathVariable("id") long id,
                                         @PathVariable("name") String name, Model model) {
        if (bindingResult.hasErrors()) {
            return "tvShowEditEpisode";
        }
        episodeService.updateEpisodeFromForm(episodeFormDTO);
        return String.format("redirect:/tv/%s/edit/episodes", name);
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
    public String addTvShow(@ModelAttribute("tv") @Valid TvShowDTO tvShowDTO, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "addTvShow";
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

    @GetMapping("/country/{name}")
    public String displayCountry(@PathVariable String name, Model model) {
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        model.addAttribute("country", name);
        model.addAttribute("tvshows", tvShowService.getTvByCountry(name));
        return "tvShowList";
    }

    @GetMapping("/year/{releaseYear}")
    public String displayYear(@PathVariable int releaseYear, Model model) {

        model.addAttribute("year", releaseYear);
        model.addAttribute("tvshows", tvShowService.getTvByYear(releaseYear));
        return "tvShowList";
    }

    @GetMapping("/network/{name}")
    public String displayNetwork(@PathVariable String name, Model model) {
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        model.addAttribute("network", name);
        model.addAttribute("tvshows", tvShowService.getTvByNetwork(name));
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
