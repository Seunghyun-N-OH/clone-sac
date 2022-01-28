package com.example.sac.domain.services;

import java.util.List;

import com.example.sac.web.dtos.EventD;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ShowS {

    void registerEvent(EventD a, List<String> subject, List<Integer> price, MultipartFile poster, MultipartFile detail);

    void getShowList(Model m);

    String getShowDetail(long eventId, Model m, RedirectAttributes ra);

}
