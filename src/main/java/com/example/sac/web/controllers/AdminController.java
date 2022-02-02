package com.example.sac.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
    @RequestMapping(value = { "/admin", "/admin/main" }, method = RequestMethod.GET)
    public String toAdminMain() {
        return "guide/adminIndex";
    }
}
