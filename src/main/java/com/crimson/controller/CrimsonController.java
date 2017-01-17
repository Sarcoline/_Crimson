package com.crimson.controller;

import com.crimson.dao.TvShowDAO;
import com.crimson.dao.UserDAO;
import com.crimson.model.TvShow;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/tv")
@Controller
public class CrimsonController {

    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private UserDAO userDAO;

    @GetMapping("/{name}")
    public String displayTvShow(Model model, @PathVariable("name") String name) {

        model.addAttribute("tv", tvShowDAO.getTvBySlug(name));
        return "tvShow";
    }

    @GetMapping("/user/{kto}")
    public String displayUser(Model model, @PathVariable("kto") String kto){
        model.addAttribute("user",kto);
        model.addAttribute("user",userDAO.getUserByName(kto));
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
        if (tvShowDAO.getAllTvShows().size() < 1) {
            TvShow tv = new TvShow();
            tv.setGenre("Fantasy");
            tv.setTitle("Game Of Thrones");
            tv.setNetwork("HBO");
            tv.setOverallRating(9.2);
            tv.setTrailerUrl("https://www.youtube.com/watch?v=EI0ib1NErqg");
            tv.setCountry("USA");
            tv.setReleaseYear(2010);
            tv.setDescription("Seven noble families fight for control of the mythical land of Westeros. " +
                    "Political and sexual intrigue abound. The primary families are the Stark, Lannister, " +
                    "and Baratheon families. Robert Baratheon, King of Westeros, asks his old friend Eddard Stark to serve as his chief advisor. " +
                    "Eddard, suspecting that his predecessor had been murdered, accepts so that he can investigate further. It turns out more than one " +
                    "family is plotting to take the throne.\n" +
                    "The Queen's family, the Lannisters, may be hatching an incestuous plot to take control. " +
                    "Across the sea, the last surviving members of the previously deposed ruling family, the Targaryens, are also plotting a return to power. " +
                    "The conflict between these families and others, including the Greyjoys, the Tullys, the Arryns, and the Tyrells, leads to war. " +
                    "Meanwhile, in the north, an ancient evil awakens. Amidst war and the political confusion, a brotherhood of misfits, " +
                    "The Night's Watch, is all that stands between the realms of men and the horrors beyond.");
            TvShow tv1 = new TvShow();
            tv1.setGenre("Drama");
            tv1.setTitle("Shameless");
            tv1.setNetwork("Showtime");
            tv1.setOverallRating(8.5);
            tv1.setTrailerUrl("https://www.youtube.com/watch?v=ITsirWLf-W8");
            tv1.setCountry("USA");
            tv1.setReleaseYear(2008);
            tv1.setDescription(
                    "Based on the UK drama, Shameless, the series follows the exploits of an alcoholic patriarch and his large, blue-collar family.\n" +
                            "Set in working-class Chicago, the Gallagher family, a working class family of eight, must survive the ups and downs of today's recession. " +
                            "With a mother who is out of her element and an alcoholic father who usually ends up passed out on the living room floor, 18-year-old daughter " +
                            "Fiona is left with the task of keeping her five younger brothers and sisters on the straight and narrow. This series is based on the UK series of the same name.");
            TvShow tv2 = new TvShow();
            tv2.setGenre("Drama");
            tv2.setTitle("Prison Break");
            tv2.setNetwork("Fox");
            tv2.setOverallRating(7.7);
            tv2.setTrailerUrl("https://www.youtube.com/watch?v=AL9zLctDJaU");
            tv2.setCountry("USA");
            tv2.setReleaseYear(2004);
            tv2.setDescription(
                    "Due to a political conspiracy, an innocent man is sent to death row and his only hope is his brother, " +
                            "who makes it his mission to deliberately get himself sent to the same prison in order to break the both of them out, " +
                            "from the inside.");
            tvShowDAO.saveTvShow(tv);
            tvShowDAO.saveTvShow(tv1);
            tvShowDAO.saveTvShow(tv2);
        }
        model.addAttribute("tvshows", tvShowDAO.getAllTvShows());
        return "tvShowList";
    }
/*
    @Transactional
    @RequestMapping(value = "/follow/{id}")
    public String follow(Model model, @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByName(auth.getName());
        TvShow tv = tvShowDAO.getTvById(id);
        if (user.getTvShows().contains(tv)) user.getTvShows().remove(tv);
        else user.getTvShows().add(tv);


        Slugify slg = new Slugify();
        return "redirect:/tv/" + slg.slugify(tv.getTitle());
    }
*/

}
