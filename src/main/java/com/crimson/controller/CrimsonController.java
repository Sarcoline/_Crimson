package com.crimson.controller;

import com.crimson.dao.RatingDAO;
import com.crimson.dao.TvShowDAO;
import com.crimson.dao.UserDAO;
import com.crimson.model.Rating;
import com.crimson.model.TvShow;
import com.crimson.model.User;
import ma.glasnost.orika.MapperFacade;
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

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/tv")
@Controller
public class CrimsonController {

    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RatingDAO ratingDAO;

    @Transactional
    @GetMapping("/{name}")
    public String displayTvShow(Model model, @PathVariable("name") String name) {
        TvShow tv = tvShowDAO.getTvBySlug(name);
        boolean follow = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = userDAO.getUserByName(auth.getName());
            if (user.getUserTvShowList().contains(tv)) follow = true;
        }

        model.addAttribute("tv", tv);
        model.addAttribute("follow", follow);
        return "tvShow";
    }

    @Transactional(readOnly = true)
    @GetMapping("/user/{kto}")
    public String displayUser(Model model, @PathVariable("kto") String kto) {
        User user = userDAO.getUserByName(kto);
        List<TvShow> tvs = user.getUserTvShowList();
        model.addAttribute("tvshows", tvs);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/genre/{name}")
    public String displayGenre(@PathVariable String name, Model model) {
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        model.addAttribute("genre", name);
        model.addAttribute("tvshows", tvShowDAO.getTvByGenre(name));
        return "tvShowList";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchResult(Model model, HttpServletRequest request) {
        model.addAttribute("search", request.getParameter("search"));
        return "searchResult";
    }

    @GetMapping("/list")
    public String tvShowList(Model model) {
        model.addAttribute("tvshows", tvShowDAO.getAllTvShows());
        return "tvShowList";
    }

    @Transactional
    @RequestMapping(value = "/follow/{id}")
    public String follow(Model model, @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByName(auth.getName());
        TvShow tv = tvShowDAO.getTvById(id);
        if (user.getUserTvShowList().contains(tv)) user.getUserTvShowList().remove(tv);
        else user.getUserTvShowList().add(tv);


        return "redirect:/tv/" + tv.getSlug();
    }

    @RequestMapping(value = "/rate/{id}")
    public String rate(Model model, @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByName(auth.getName());
        TvShow tv = tvShowDAO.getTvById(id);
        Rating rating = null;
        try {
            rating = ratingDAO.getR(tv.getId(), user.getId());
        } catch (NoResultException e) {
        }


        if (rating != null) {
            ratingDAO.deleteRating(rating);
        } else {
            Rating ratingNew = new Rating();
            ratingNew.setUserRating(user);
            ratingNew.setTvShowRating(tv);
            ratingNew.setValue(6);
            ratingDAO.saveRating(ratingNew);
        }


        return "redirect:/tv/" + tv.getSlug();
    }

}
