package com.crimson.mvc.controller;

import com.crimson.core.dto.*;
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
        String review = "<h1>Heading</h1>\n" +
                "\n" +
                "<p>Lorem ipsum dolor sit <strong>amet</strong>, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. <a href=\"#\">This is a link</a></p>\n" +
                "\n" +
                "<ul>\n" +
                "    <li>Item</li>\n" +
                "    <li>Item</li>\n" +
                "    <li>Item</li>\n" +
                "</ul>\n" +
                "\n" +
                "<h2>Heading</h2>\n" +
                "\n" +
                "<p>Ut enim ad <em>minim</em> veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>\n" +
                "\n" +
                "<p>\n" +
                "    Test paragraph\n" +
                "</p>";
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

        episodes.sort(Comparator.comparing(EpisodeDTO::getNumber));
        episodes.sort(Comparator.comparing(EpisodeDTO::getSeason));
        int seasons = episodes.isEmpty() ? 0 :  episodes.get(episodes.size()-1).getSeason();
        List<CommentDTO> comments = tv.getComments();
        comments.sort(Comparator.comparing(CommentDTO::getDate).reversed());
        model.addAttribute("comments", comments);
        model.addAttribute("tv", tv);
        model.addAttribute("episodes", episodes);
        model.addAttribute("seasons", seasons);
        model.addAttribute("rating", rating);
        model.addAttribute("follow", follow);
        model.addAttribute("review", review);
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
        model.addAttribute("name", name);
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


    @GetMapping("/list")
    public String tvShowList(Model model) {
        model.addAttribute("tvSize", tvShowService.tvShowsSize());
        return "tvShowList";
    }

    @GetMapping(value = "/{name}/edit/episodes/addSearch")
    public String searchAddEpisode(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        return "addEpisodesFromJson";
    }

    @RequestMapping(value = "/{name}/edit/episodes/addSearch/add", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public void postSearchAddEpisode(@RequestParam("title") String title, @RequestParam("episode") int number,
                                     @RequestParam("season") int season,  @RequestParam("summary") String summary,
                                     @RequestParam("releaseDate") String releaseDate,
                                     @PathVariable("name") String name) {
        EpisodeFromJson episode = new EpisodeFromJson();
        episode.setTitle(title);
        episode.setReleaseDate(releaseDate);
        episode.setEpisode(number);
        episode.setSeason(season);
        episode.setSummary(summary);
        episode.setIdTvShow(tvShowService.getTvBySlug(name).getId());
        episodeService.saveEpisodeJSON(episode);
    }

    @GetMapping(value = "/{name}/reviews/write")
    @Secured({"ROLE_AUTHOR","ROLE_ADMIN"})
    public String writeReview(@PathVariable("name") String title, Model model) {
        TvShowDTO tv = tvShowService.getTvBySlug(title);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        model.addAttribute("tv", tv);
        model.addAttribute("user", user);
        model.addAttribute("review", new ReviewDTO());
        return "writeReview";
    }

    @RequestMapping(value = "/{name}/reviews/write", method = RequestMethod.POST)
    @Secured({"ROLE_AUTHOR", "ROLE_ADMIN"})
    public String postReview(@ModelAttribute("review") ReviewDTO reviewDTO, @PathVariable("name") String title) {
        TvShowDTO tv = tvShowService.getTvBySlug(title);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        reviewDTO.setAuthor(user);
        reviewDTO.setTvShow(tv);
        return String.format("redirect:/tv/%s", title);
    }

    @GetMapping(value = "/{name}/reviews/{id}")
    public String displayReview(Model model) {
        return "review";
    }
}
