package com.crimson.controller;

import com.crimson.service.TvShowService;
import com.crimson.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by Meow on 30.12.2016.
 */
@Controller
public class ImagesController {

    @Autowired
    private TvShowService tvShowService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/images/tv/{name}/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] GetShowImage(@PathVariable String image, @PathVariable String name) throws IOException {
        return tvShowService.getTvPictures(name).get(image);
    }
    @ResponseBody
    @RequestMapping(value = "/images/user/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] GetUserImage(@PathVariable String name) throws IOException {
        return userService.getUserByName(name).getProfilePic();
    }
}
