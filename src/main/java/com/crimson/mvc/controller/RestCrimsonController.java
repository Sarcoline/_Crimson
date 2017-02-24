package com.crimson.mvc.controller;

import com.crimson.core.dto.TvShowSearchDTO;
import com.crimson.core.service.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/api")
@RestController
public class RestCrimsonController {

    @Autowired
    private TvShowService tvShowService;

    @RequestMapping(value = "/search/{pattern}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<TvShowSearchDTO> searchResult(@PathVariable("pattern") String pattern, HttpServletRequest request) {
        return tvShowService.searchTvShow(pattern);
    }

    @RequestMapping(value = "/rating/{min}/{max}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<TvShowSearchDTO> searchRating(@PathVariable("min") double min, @PathVariable("max") double max, HttpServletRequest request) {
        return tvShowService.filterTvShows(min,max);
    }

    @GetMapping(value = "/tvshows/{page}", produces = "application/json")
    @ResponseBody
    public List<TvShowSearchDTO> getTvShowsOnPage(@PathVariable("page") int page) {
        return tvShowService.tvShowsPaginationList(page);
    }
}
