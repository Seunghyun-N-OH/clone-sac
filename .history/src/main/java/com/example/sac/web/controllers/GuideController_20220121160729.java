package com.example.sac.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuideController {

    @RequestMapping(value = "/sitemap", method = RequestMethod.GET)
    public String toSitemap() {
        return "guide/sitemap";
    }
}
