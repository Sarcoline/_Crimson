package com.crimson.mvc.controller;

import com.crimson.core.dto.*;
import com.crimson.core.model.User;
import com.crimson.core.service.MailService;
import com.crimson.core.service.ReviewService;
import com.crimson.core.service.TvShowService;
import com.crimson.core.service.UserService;
import com.crimson.core.validator.UserValidator;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TvShowService tvShowService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private MailService mailService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        model.addAttribute("tvShows", tvShowService.getAllTvShowByMaxRating());
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "confirmed", required = false) String confirmed,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error", "Invalid username and password!");
        }
        if (registered != null) {
            model.addAttribute("msg", "We sent you verification email.");
        }
        if (confirmed != null) {
            model.addAttribute("msg", "Verification complete. You can log in now!");
        }
        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }
        return "login";

    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registration(Model model,  @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Invalid registration token.");
        }
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@Valid UserDTO userDTO, BindingResult bindingResult) throws IOException, MessagingException {
        userValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userDTO.getUploadedPic().isEmpty()) {
            userDTO.setUploadedPic(null);
        }

        String token = java.util.UUID.randomUUID().toString();
        userDTO.setToken(token);
        String body = String.format("<h1>Your account has been created</h1><h3>Confirm your email</h3>" +
                "<a href='http://localhost:8080/confirm/%s'>Click to confirm</a>", token);
        mailService.sendMail(userDTO.getEmail(), "Crimson - Confirm your email", body);
        userService.saveUser(userDTO);
        return "redirect:/login?registered";
    }

    @GetMapping(value = "/confirm/{token}")
    public String confirmAccount(@PathVariable("token") String token) {
        if (!userService.confirmUser(token)) return "redirect:/register?error";
        return "redirect:/login?confirmed";
    }

    @RequestMapping(value = "/updatePicture", method = RequestMethod.POST)
    public String updatePicture(@RequestParam("fileUpload") MultipartFile dto) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        userService.changeProfilePic(user, dto);
        return "redirect:/user/edit";
    }

    @GetMapping("/user/{name}")
    public String displayUser(Model model, @PathVariable("name") String name) {

        UserDTO user = userService.getUserByName(name);
        List<TvShowDTO> tvs = user.getTvShows();
        List<TvShowSearchDTO> favorites = userService.getUserTvShowsSortedByMaxRating(user);
        List<EpisodeDTO> watchedEpisodes = user.getEpisodes();
        List<Long> watchedEpisodesId = new ArrayList<>();
        watchedEpisodes.forEach(episode -> watchedEpisodesId.add(episode.getId()));
        List<EpisodeDTO> upcoming = userService.getAllUpcomingUserEpisodes(user, tvs, watchedEpisodes);

        model.addAttribute("tvshows", tvs);
        model.addAttribute("watchedEpisodes", Lists.reverse(watchedEpisodes));
        model.addAttribute("watchedEpisodesId", watchedEpisodesId);
        model.addAttribute("upcomimgEpisodes", upcoming);
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
            model.addAttribute("settings", userDTO.getSetting());
            return "userEdit";
        } else return "redirect:/";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    @Secured("ROLE_USER")
    public String postEditUser(@Valid UserDTO userDTO, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return "redirect:/tv/user/edit?error";
        }
        userService.updateUser(userDTO);
        return "redirect:/user/edit";
    }

    @RequestMapping(value = "/user/delete/", method = RequestMethod.GET)
    @Secured("ROLE_USER")
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        userService.deleteUser(user);
        request.logout();
        response.sendRedirect("/");
    }

    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    public String changeUserPassword(@Valid PasswordDTO passwordDTO, BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByName(auth.getName());
        if (bindingResult.hasErrors()) {
            return "changePassword";
        }
        if (!userService.checkOldPassword(user, passwordDTO.getOldPassword()))
            return "redirect:/user/updatePassword?wrongPassword";
        if (!Objects.equals(passwordDTO.getPassword(), passwordDTO.getMatchingPassword()))
            return "redirect:/user/updatePassword?mismatch";
        else userService.updatePassword(user, passwordDTO.getPassword());

        return String.format("redirect:/user/%s", user.getName());
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
        model.addAttribute("passwordDTO", new PasswordDTO());
        return "changePassword";
    }

    @GetMapping(value = "/user/resetPassword")
    public String resetPassword(Model model, @RequestParam(value = "usernotfound", required = false) String usernotfound) {
        if (usernotfound != null) {
            model.addAttribute("error", "User with that mail doesn't exist");
        }
        return "resetPassword";
    }

    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    public String postResetPassword(@RequestParam("email") String email) throws MessagingException {
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO == null) {
            return "redirect:/user/resetPassword?usernotfound";
        }

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(userDTO, token);
        String body = String.format("<h3>Click to reset your password</h3>" +
                "<a href='http://localhost:8080/user/changePassword?id=%s&token=%s'>Click to reset</a>", userDTO.getId(), token);
        mailService.sendMail(userDTO.getEmail(), "Crimson - Reset your password", body);
        return "resetPasswordSent";
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public String changePassword(Model model, @RequestParam("id") long id,
                                 @RequestParam("token") String token) {
        String result = userService.validatePasswordResetToken(id, token);
        if (result != null) {
            return "redirect:/login?error";
        }
        model.addAttribute("passwordResetDTO", new PasswordResetDTO());

        return "resetPasswordInput";
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
    public String savePassword(@Valid PasswordResetDTO passwordResetDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "resetPasswordInput";
        }
        User user =
                (User) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        userService.changeUserPassword(user, passwordResetDTO.getPassword());
        userService.deletePasswordResetToken(passwordResetDTO.getToken());

        return "redirect:/login";
    }
}
