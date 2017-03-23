package com.crimson.mvc.controller;

import com.crimson.core.dto.*;
import com.crimson.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api")
@RestController
public class RestCrimsonController {

    @Autowired
    private TvShowService tvShowService;
    @Autowired
    private UserService userService;
    @Autowired
    private EpisodeService episodeService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/search/{pattern}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<TvShowSearchDTO> searchResult(@PathVariable("pattern") String pattern, HttpServletRequest request) {
        return tvShowService.searchTvShow(pattern);
    }

    @GetMapping(value = "/tvshows/{page}", produces = "application/json")
    @ResponseBody
    public List<TvShowSearchDTO> getTvShowsOnPage(@PathVariable("page") int page) {
        return tvShowService.tvShowsPaginationList(page);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER")
    public void addComment(@RequestBody CommentDTO commentDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        TvShowDTO tvShowDTO = tvShowService.getTvById(commentDTO.getIdTvShow());
        commentDTO.setTvShow(tvShowDTO);
        commentDTO.setUser(user);
        commentDTO.setDate(LocalDate.now());
        commentService.save(commentDTO);
    }

    @RequestMapping(value = "/watched", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER")
    public void watched(@RequestParam("id") long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        EpisodeDTO episode = episodeService.getEpisodeById(id);
        if (episodeService.checkWatched(user, episode)) episodeService.deleteUserFromEpisode(user, episode);
        else episodeService.addUser2Episode(user, episode);
    }

    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER")
    public void rate(@RequestParam("id") long id, @RequestParam("value") int value) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        TvShowDTO tv = tvShowService.getTvById(id);
        ratingService.saveUserRating(user, tv, value);
    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @Secured("ROLE_USER")
    public String follow(@RequestParam("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        TvShowDTO tv = tvShowService.getTvById(id);

        if (userService.checkFollow(user, tv)) userService.deleteTvShowFromUser(user, tv);
        else userService.addTvShow2User(user, tv);

        return String.format("redirect:/tv/%s", tv.getSlug());
    }

    @RequestMapping(value = "/filter/{page}", method = RequestMethod.POST)
    public FilterResponseDTO filter(@RequestBody SearchFilterParameters parameters, @PathVariable("page") int page) {
        return tvShowService.filter(parameters, page);
    }
}
