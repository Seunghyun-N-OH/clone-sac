package com.example.sac.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.example.sac.domain.services.NoticeS;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

// 주석 업데이트 220126

@Controller
public class NewsController {

    public NewsController(NoticeS ns) {
        this.ns = ns;
    }

    private final NoticeS ns;

    // 공지사항 메인페이지로 이동
    // 보여줄 정보는 거기가면 불러옴
    @RequestMapping(value = "/sacnews/notice", method = RequestMethod.GET)
    public String toNotice() {
        return "sacnews/notice";
    }

    // 공지사항 검색
    @RequestMapping(value = "/sacnews/notice/search", method = RequestMethod.GET)
    public String targetedSearch(@RequestParam(defaultValue = "all") String target, @RequestParam String key, Model m) {
        // 결과물 담아갈 모델이랑같이, 사용자가 선택한 검색범주(전체/제목/내용), 검색어를 가져감
        return ns.searchNotice(target, key, m);
    }

    // 처음 메인 들어오면 빈페이지여서 리스트 불러오는 작업
    @RequestMapping(value = "/sacnews/notice/init", method = RequestMethod.GET)
    public String loadList(Model m) {
        // 분류없이 그냥 다불러갈거니 리스트 담아갈 모델만가지고 서비스로
        return ns.loadList(m);
    }

    // 카테고리 버튼 누르면 그 카테고리만 보여주기
    @RequestMapping(value = "/sacnews/notice/target/{cat}", method = RequestMethod.GET)
    public String getTargetedList(Model m, @PathVariable String cat) {
        // 선택한 카테고리가 뭔지랑, 담아갈 모델 가지고 서비스로
        return ns.loadTargetedList(m, cat);
    }

    @RequestMapping(value = "/sacnews/notice/{notice}", method = RequestMethod.GET)
    public String showDetail(@PathVariable long notice, Model m) {
        // 보려는 게시글 번호랑 모델 가지고 서비스로
        return ns.noticeDetail(notice, m);
    }

    // 게시글 보다가 첨부파일 다운받고싶어하면
    @RequestMapping(value = "/sacnews/notice/{notice}/attach", method = RequestMethod.GET)
    public void downFileWithNoticeNumber(@PathVariable long notice, @RequestParam long fno,
            HttpServletResponse response) {
        try {
            // 보고있던 게시글 번호랑, 받으려하는 첨부파일의 fno, 파일 내려줄 response 가지고 서비스로
            ns.downService(notice, fno, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
