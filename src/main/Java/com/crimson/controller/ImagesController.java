package com.crimson.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Meow on 30.12.2016.
 */
@Controller
public class ImagesController {

    @Autowired
    ServletContext context;

    @ResponseBody
    @RequestMapping(value = "images/{name}/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] GetImage(@PathVariable String image, @PathVariable String name) throws IOException {
        InputStream in = context.getResourceAsStream(String.format("/images/%s/%s.jpg", name, image));
        return IOUtils.toByteArray(in);
    }
}
