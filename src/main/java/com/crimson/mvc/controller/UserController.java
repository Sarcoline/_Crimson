package com.crimson.mvc.controller;

import com.crimson.core.dto.UserDTO;
import com.crimson.core.service.TvShowService;
import com.crimson.core.service.UserService;
import com.crimson.core.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TvShowService tvShowService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            @RequestParam(value = "error", required = false) String error,
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
            model.addAttribute("msg", "Registered successfully! You can log in.");
        }
        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }
        return "login";

    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@Valid UserDTO userDTO, BindingResult bindingResult) throws IOException {
        userValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userDTO.getUploadedPic().isEmpty()) {
            userDTO.setUploadedPic(null);
        }
        userService.saveUser(userDTO);
        return "redirect:/login?registered";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("tvShows", tvShowService.getAllTvShowByMaxRating());
        return "index";
    }

//    @RequestMapping(value = "/upload", method = RequestMethod.GET)
//    public String showUploadForm(HttpServletRequest request, Model model) {
//        return "upload";
//    }
//
//    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
//    public String handleFileUpload(HttpServletRequest request,
//                                   @RequestParam("fileUpload") MultipartFile dto) throws Exception {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDTO user = userService.getUserByName(auth.getName());
//        user.setProfilePic(dto.getBytes());
//        userService.updateUser(user);
//        return "redirect:/";
//    }
}
