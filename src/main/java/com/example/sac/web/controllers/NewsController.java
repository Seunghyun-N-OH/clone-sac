package com.example.sac.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewsController {

    @RequestMapping(value = "/sacnews/notice", method = RequestMethod.GET)
    public String toNotice(@RequestParam(defaultValue = "1") int p) {
        return "sacnews/notice";
    }

    @RequestMapping(value = "admin/sacnews/notice/post", method = RequestMethod.GET)
    public String toPostPage() {
        return "sacnews/new";
    }
}
