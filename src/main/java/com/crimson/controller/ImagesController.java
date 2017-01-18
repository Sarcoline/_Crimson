package com.crimson.controller;

import com.crimson.dao.TvShowDAO;
import com.crimson.dao.UserDAO;
import com.crimson.model.TvShow;
import com.crimson.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * Created by Meow on 30.12.2016.
 */
@Controller
public class ImagesController {

    @Autowired
    private ServletContext context;

    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private UserDAO userDAO;

    @ResponseBody
    @RequestMapping(value = "/images/tv/{name}/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] GetShowImage(@PathVariable String image, @PathVariable String name) throws IOException {
        TvShow tv = tvShowDAO.getTvBySlug(name);
        return tv.getPictures().get(image);
    }
    @ResponseBody
    @RequestMapping(value = "/images/user/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] GetUserImage(@PathVariable String name) throws IOException {
        User user = userDAO.getUserByName(name);
        return user.getProfilePic();
    }
}
