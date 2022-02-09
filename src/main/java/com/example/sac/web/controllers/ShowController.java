package com.example.sac.web.controllers;

import java.security.Principal;
import java.time.LocalDateTime;

import com.example.sac.domain.services.ShowS;
import com.example.sac.web.dtos.EventD;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShowController {

    public ShowController(ShowS s) {
        this.ss = s;
    }

    private final ShowS ss;

    @RequestMapping(value = { "/show/show_list", "/show" }, method = RequestMethod.GET)
    public String toShowList(Model m) {
        return ss.getShowList(m);
    }

    @RequestMapping(value = "/show/{eventId}", method = RequestMethod.GET)
    public String toDetail(@PathVariable long eventId, Model m, RedirectAttributes ra) {
        return ss.getShowDetail(eventId, m, ra);
    }

    @RequestMapping(value = "/show/{eventId}/ticket", method = RequestMethod.GET)
    public String toPurchase(@PathVariable long eventId, Model m) {
        return ss.toPurchaseS(eventId, m);
    }

    @RequestMapping(value = "/show/{eventId}/ticket", method = RequestMethod.POST)
    public String purchaseTicket(@PathVariable long eventId, Model m, EventD event, Principal p, String ticketClass,
            int ticketPrice, LocalDateTime showTime) {
        return ss.purchaseTicketS(eventId, m, event, p, ticketClass, ticketPrice, showTime);
    }

}
