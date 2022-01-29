package com.example.sac.domain.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.example.sac.web.dtos.NoticeD;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface NoticeS {

    String postNewNotice(NoticeD data, List<MultipartFile> attach, String string);

    String loadList(Model m);

    String noticeDetail(long notice, Model m);

    String loadTargetedList(Model m, String cat);

    String searchNotice(String target, String key, Model m);

    String noticeEdit(long targetNo, Model m);

    String editNotice(NoticeD data, List<MultipartFile> attach, List<Long> dfiles, String string);

    String deleteNotice(long no);

    void downService(long noticeNumber, long fileNumber, HttpServletResponse response)
            throws FileNotFoundException, IOException;

}
