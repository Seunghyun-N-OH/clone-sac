package com.example.sac.web.controllers;

@Controller
public class GuideController {

    @RequestMapping(value = "/sitemap", method = RequestMethod.GET)
    public String toSitemap() {
        return "guide/sitemap";
    }
}
