package com.crimson.controller;

import com.crimson.dao.UserDAO;
import com.crimson.dto.UserDTO;
import com.crimson.model.User;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Meow on 30.12.2016.
 */
@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    ApplicationContext context;

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
    public String registration(@Valid UserDTO userDTO, BindingResult bindingResult, Model model, HttpServletRequest request) throws IOException {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        User user = mapperFacade.map(userDTO, User.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        InputStream in = context.getResource("classpath:/images/user/user.jpg").getInputStream();
        user.setProfilePic(IOUtils.toByteArray(in));
        userDAO.saveUser(user);

//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
//        token.setDetails(new WebAuthenticationDetails(request));
//        Authentication authenticatedUser = authenticationManager.authenticate(token);
//        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

        return "redirect:/login?registered";
    }
}
