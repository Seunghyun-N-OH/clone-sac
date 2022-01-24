package com.example.sac.domain.services;

import com.example.sac.web.dtos.NoticeD;

import org.springframework.ui.Model;

public interface NoticeS {

    void postNewNotice(NoticeD data);

    String loadList(Model m, String category);

    String noticeDetail(long notice, Model m);

    String loadTargetedList(Model m, String cat);

}
