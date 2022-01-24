package com.example.sac.web.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import com.example.sac.domain.services.NoticeS;
import com.example.sac.web.dtos.MembershipD;
import com.example.sac.web.dtos.NoticeD;
import com.google.gson.JsonObject;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class NewsController {

    public NewsController(NoticeS ns) {
        this.ns = ns;
    }

    private static long fileNo = 100000000L;
    private final NoticeS ns;

    @RequestMapping(value = "/sacnews/notice", method = RequestMethod.GET)
    public String toNotice(@RequestParam(defaultValue = "1") int p, Model m) {
        ns.loadList(p, m);
        return "sacnews/notice";
    }

    @RequestMapping(value = "/sacnews/notice/{notice}", method = RequestMethod.GET)
    public String showDetail(@PathVariable long notice, Model m) {
        System.out.println("controller caught");
        System.out.println("notice no. : " + notice);

        return ns.noticeDetail(notice, m);
    }

    @RequestMapping(value = "/admin/sacnews/notice/post", method = RequestMethod.GET)
    public String toPostPage() {
        return "sacnews/new";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/file/notice", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public String noticeAttachUpload(@RequestParam("file") MultipartFile f) {
        JsonObject jo = new JsonObject();
        String url = "/uploads/notice/includingIMGs/";
        ClassPathResource cpr = new ClassPathResource("static" + url);
        String type = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf("."));
        String newFilename = Long.toString(fileNo++) + type;

        try {
            File fileRoot = cpr.getFile();
            File tDirectory = new File(fileRoot, newFilename);
            f.transferTo(tDirectory);
            jo.addProperty("url", url + newFilename);
            jo.addProperty("responseCode", "success");
            System.out.println("success");
        } catch (IOException e) {
            jo.addProperty("responseCode", "failed");
            System.out.println("failed" + e);
        }
        return jo.toString();
    }

    @RequestMapping(value = "/admin/sacnews/notice/post", method = RequestMethod.POST)
    public String postNewNotice(NoticeD data, Principal p) {
        data.setDrafter(MembershipD.builder().userId(p.getName()).build());
        // set drafter with principal.username, which is userId of admin
        ns.postNewNotice(data);
        return "redirect:/sacnews/notice";
    }

}
