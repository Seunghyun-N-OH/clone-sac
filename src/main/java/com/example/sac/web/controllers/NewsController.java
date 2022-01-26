package com.example.sac.web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.example.sac.domain.services.NoticeS;
import com.example.sac.domain.services.functions.UpAndDownFile;
import com.example.sac.web.dtos.MembershipD;
import com.example.sac.web.dtos.NoticeD;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

    // admin전용, 공지작성중 썸머노트 텍스트에어리어에 이미지 던지면 업로드하고 미리보기 할 url포함 정보 돌려주기
    @ResponseBody
    @RequestMapping(value = "/admin/file/notice", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public String noticeAttachUpload(@RequestParam("file") MultipartFile f) {
        return UpAndDownFile.uploadFile_summernote(f);
    }

    // 게시글 제목 누르면 상세페이지로 들어가기
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

    // TODO 이후 admincontroller 따로 생성해 옮길지 결정
    // admin전용, 공지작성 누르면 글쓰러 보내주기
    @RequestMapping(value = "/admin/sacnews/notice/post", method = RequestMethod.GET)
    public String toPostPage() {
        return "sacnews/new";
    }

    // TODO 이후 admincontroller 따로 생성해 옮길지 결정
    // admin전용, 내용 다 쓴 공지 등록버튼 누르면 등록해주기
    @RequestMapping(value = "/admin/sacnews/notice/post", method = RequestMethod.POST)
    public String postNewNotice(NoticeD data, MultipartHttpServletRequest htsr, Principal p) {
        // 있을지 모르는 첨부파일을 일단 뽑고
        Set<MultipartFile> att = Set.copyOf(htsr.getFiles("attach"));
        // 자동입력되어야 할 작성자이름 principal에서 뽑아서 지정해주고
        data.setDrafter(MembershipD.builder().userId(p.getName()).build());
        // 작성자정보가 입력된 공지정보, 있을지모르는 첨부파일 들고 서비스로
        ns.postNewNotice(data, att);
        return "redirect:/sacnews/notice";
    }

    // TODO 이후 admincontroller 따로 생성해 옮길지 결정
    // TODO 파일수정 포함시키기
    // admin전용, 공지 수정할거 다 수정하고 수정적용 버튼 누르면 할일
    @RequestMapping(value = "/admin/sacnews/notice", method = RequestMethod.PUT)
    public String editSubmit(NoticeD data, String publisher) {
        data.setDrafter(MembershipD.builder().userId(publisher).build());
        ns.editNotice(data);
        return "redirect:/sacnews/notice/" + data.getNo();
    }

    // TODO 이후 admincontroller 따로 생성해 옮길지 결정
    // admin전용, 공지 삭제하고싶을 때
    @RequestMapping(value = "/admin/sacnews/notice", method = RequestMethod.DELETE)
    public String deleteSubmit(long no) {
        // 삭제하려는 공지 번호 들고 서비스가기
        ns.deleteNotice(no);
        return "redirect:/sacnews/notice";
    }

    // TODO 이후 admincontroller 따로 생성해 옮길지 결정
    // admin전용, 보고있는 상세페이지의 공지를 수정하고싶을때
    @RequestMapping(value = "/admin/sacnews/notice", method = RequestMethod.GET)
    public String toEdit(@RequestParam long no, Model m) {
        // 보고있는 공지의 id들고 서비스로
        return ns.noticeEdit(no, m);
    }
}
