package com.example.sac.web.controllers;

import java.util.List;

import com.example.sac.domain.services.ShowS;
import com.example.sac.web.dtos.EventD;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShowController {

    public ShowController(ShowS s) {
        this.ss = s;
    }

    private final ShowS ss;

    @RequestMapping(value = "/show/show_list", method = RequestMethod.GET)
    public String toShowList(Model m) {
        return ss.getShowList(m);
    }

    @RequestMapping(value = "/show/{eventId}", method = RequestMethod.GET)
    public String toDetail(@PathVariable long eventId, Model m, RedirectAttributes ra) {
        return ss.getShowDetail(eventId, m, ra);
    }

    // (admin functions) ###################

    @RequestMapping(value = "/admin/show/register", method = RequestMethod.GET)
    public String toRegister() {
        return "show/register";
    }

    @RequestMapping(value = "/admin/show/register", method = RequestMethod.POST)
    public String submitRegister(EventD a, @RequestParam List<String> subject, @RequestParam List<Integer> price,
            MultipartFile poster_file, MultipartHttpServletRequest htsr) {
        System.out.println(a);
        ss.registerEvent(a, subject, price, poster_file, List.copyOf(htsr.getFiles("detailImage_file")));
        return "redirect:/show/show_list";
    }
}
