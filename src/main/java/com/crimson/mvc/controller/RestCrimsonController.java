package com.crimson.mvc.controller;

import com.crimson.core.dto.*;
import com.crimson.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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


    //returns search results for given pattern
    @RequestMapping(value = "/search/{pattern}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<TvShowSearchDTO> searchResult(@PathVariable("pattern") String pattern) {
        return tvShowService.searchTvShow(pattern);
    }

    //returns list of tvshows for given page
    @GetMapping(value = "/tvshows/{page}", produces = "application/json")
    @ResponseBody
    public List<TvShowSearchDTO> getTvShowsOnPage(@PathVariable("page") int page) {
        return tvShowService.tvShowsPaginationList(page);
    }

    //handles adding comments to tvshow
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER")
    public void addComment(@RequestBody CommentDTO commentDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        commentService.save(commentDTO, auth.getName());
    }

    //handles user request to mark episode as watched
    @RequestMapping(value = "/watched", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER")
    public void watched(@RequestParam("id") long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (episodeService.checkWatched(auth.getName(), id)) episodeService.deleteUserFromEpisode(auth.getName(), id);
        else episodeService.addUser2Episode(auth.getName(), id);
    }

    //handles user request for marking whole season of tvshow as watched
    @RequestMapping(value = "/watchedseason", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER")
    public void watchedSeason(@RequestParam("season") int season, @RequestParam("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        episodeService.addUserToSeason(auth.getName(), season, slug);
    }

    //handles user request for rating tvshow
    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER")
    @ResponseBody
    public String rate(@RequestParam("id") long id, @RequestParam("value") int value) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ratingService.saveUserRating(auth.getName(), id, value);
        return "rated";
    }

    //handles user request to follow tvshow
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @Secured("ROLE_USER")
    @ResponseBody
    public String follow(@RequestParam("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String response;
        if (userService.checkFollow(auth.getName(), id)) {
            userService.deleteTvShowFromUser(auth.getName(), id);
            response = "unfollow";
        } else {
            userService.addTvShow2User(auth.getName(), id);
            response = "follow";
        }
        return response;
    }

    //returns list of tvshows for given parameters
    @RequestMapping(value = "/filter/{page}", method = RequestMethod.POST)
    public FilterResponseDTO filter(@RequestBody SearchFilterParameters parameters, @PathVariable("page") int page) {
        return tvShowService.filter(parameters, page);
    }

    //adds episodes from external api
    @RequestMapping(value = "/{name}/add", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public void addEpisodesFromJsonPost(@RequestBody List<EpisodeFromJson> episodes,
                                        @PathVariable("name") String name) {
        episodeService.saveEpisodeJSON(episodes, tvShowService.getDisplayBySlug(name).getId());
    }

    //handles updating user settings
    @RequestMapping(value = "/updateSettings", method = RequestMethod.POST)
    public void updateSettings(@RequestParam("days") int days, @RequestParam("send") boolean send) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.updateSettings(auth.getName(), days, send);
    }

    //user set himself as adult
    @RequestMapping(value = "/setadult", method = RequestMethod.POST)
    public void setIsAdult(@RequestParam("id") long id) {
        userService.setIsAdult(id);
    }
}
