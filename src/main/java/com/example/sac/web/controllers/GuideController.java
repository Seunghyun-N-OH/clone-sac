package com.example.sac.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GuideController {

    @RequestMapping(value = "/sitemap", method = RequestMethod.GET)
    public String toSitemap() {
        return "guide/sitemap";
    }
}
