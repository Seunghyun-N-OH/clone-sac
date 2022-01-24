package com.example.sac.domain.services;

import com.example.sac.web.dtos.NoticeD;

import org.springframework.ui.Model;

public interface NoticeS {

    void postNewNotice(NoticeD data);

    void loadList(int p, Model m);

    String noticeDetail(long notice, Model m);

}
