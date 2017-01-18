package com.crimson.controller;

import com.crimson.dao.TvShowDAO;
import com.crimson.dao.UserDAO;
import com.crimson.model.TvShow;
import com.crimson.model.User;
import com.github.slugify.Slugify;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RequestMapping("/tv")
@Controller
public class CrimsonController {

    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private UserDAO userDAO;

    @Transactional
    @GetMapping("/{name}")
    public String displayTvShow(Model model, @PathVariable("name") String name) {
        TvShow tv =  tvShowDAO.getTvBySlug(name);
        boolean follow = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = userDAO.getUserByName(auth.getName());
            if (user.getUsers2TvShow().contains(tv)) follow = true;
        }

        model.addAttribute("tv",tv);
        model.addAttribute("follow",follow);
        return "tvShow";
    }

    @Transactional(readOnly = true)
    //@Secured("ROLE_USER")
    @GetMapping("/user/{kto}")
    public String displayUser(Model model, @PathVariable("kto") String kto){
        User user = userDAO.getUserByName(kto);
        Set<TvShow> tvs = user.getUsers2TvShow();
        model.addAttribute("tvshows", tvs);
        model.addAttribute("user",user);
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
        if (user.getUsers2TvShow().contains(tv)) user.getUsers2TvShow().remove(tv);
        else user.getUsers2TvShow().add(tv);

        Slugify slg = new Slugify();
        return "redirect:/tv/" + slg.slugify(tv.getTitle());
    }


}
