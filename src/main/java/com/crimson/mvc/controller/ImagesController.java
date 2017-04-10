package com.crimson.mvc.controller;

import com.crimson.core.service.TvShowService;
import com.crimson.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class ImagesController {

    @Autowired
    private TvShowService tvShowService;

    @Autowired
    private UserService userService;

    //returns image for given tvshow slug and image name
    @ResponseBody
    @RequestMapping(value = "/images/tv/{name}/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] GetShowImage(@PathVariable String image, @PathVariable String name) throws IOException {
        return tvShowService.getTvPics(name, image);
    }

    //returns profile picture of user
    @ResponseBody
    @RequestMapping(value = "/images/user/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] GetUserImage(@PathVariable String name) throws IOException {
        return userService.getUserProfilePicture(name);

    }
}
