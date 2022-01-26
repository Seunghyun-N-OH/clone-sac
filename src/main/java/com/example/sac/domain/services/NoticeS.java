package com.example.sac.domain.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.example.sac.web.dtos.NoticeD;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface NoticeS {

    void postNewNotice(NoticeD data, Set<MultipartFile> attach);

    String loadList(Model m);

    String noticeDetail(long notice, Model m);

    String loadTargetedList(Model m, String cat);

    String searchNotice(String target, String key, Model m);

    String noticeEdit(long targetNo, Model m);

    void editNotice(NoticeD data, Set<MultipartFile> attach);

    void deleteNotice(long no);

    void downService(long noticeNumber, long fileNumber, HttpServletResponse response)
            throws FileNotFoundException, IOException;

}
