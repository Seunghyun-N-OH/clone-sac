package com.example.sac.web.controllers;

import java.security.Principal;
import java.util.List;

import com.example.sac.SecuritiyThings.service.MemberS;
import com.example.sac.domain.services.NoticeS;
import com.example.sac.domain.services.ShowS;
import com.example.sac.domain.services.functions.UpAndDownFile;
import com.example.sac.web.dtos.EventD;
import com.example.sac.web.dtos.NoticeD;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    public AdminController(ShowS ss, MemberS ms, NoticeS ns) {
        this.ss = ss;
        this.ms = ms;
        this.ns = ns;
    }

    // get page functions ##########################################################

    private final ShowS ss;
    private final MemberS ms;
    private final NoticeS ns;

    // member functions ############################################################

    // show functions ##############################################################

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String getIndexRotator(Model m) {
        ns.getNoticeIndex(m);
        return ss.getShowIndex(m);
    }

    @RequestMapping(value = { "/admin", "/admin/main" }, method = RequestMethod.GET)
    public String toAdminMain() {
        return "guide/adminIndex";
    }

    @RequestMapping(value = "/admin/show/register", method = RequestMethod.GET)
    public String toRegister() {
        return "show/register";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/show/eventNewsImage", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public String eventNewsImageUpload(@RequestParam("file") MultipartFile f) {
        return UpAndDownFile.uploadFile_summernote(f);
    } // 게시글 제목 누르면 상세페이지로 들어가기

    @RequestMapping(value = "/admin/show/register", method = RequestMethod.POST)
    public String submitRegister(EventD a, @RequestParam List<String> subject, @RequestParam List<Integer> price,
            MultipartFile poster_file, MultipartHttpServletRequest htsr) {
        System.out.println(a);
        ss.registerEvent(a, subject, price, poster_file, List.copyOf(htsr.getFiles("detailImage_file")));
        return "redirect:/show/show_list";
    }

    @RequestMapping(value = "/admin/show/register", method = RequestMethod.PUT)
    public String submitEdit(EventD a, @RequestParam List<String> subject, @RequestParam List<Integer> price,
            MultipartFile poster_file, MultipartHttpServletRequest htsr, String deletePoster,
            @RequestParam(required = false) List<Long> deleteDetails) {
        return ss.saveEditedEvent(a, subject, price, poster_file,
                List.copyOf(htsr.getFiles("detailImage_file")), deletePoster, deleteDetails);
    }

    @RequestMapping(value = "/admin/show/manage", method = RequestMethod.PUT)
    public String toEditPage(@RequestParam long evid, Model m, RedirectAttributes ra) {
        ss.getShowDetail(evid, m, ra);
        return "show/editPage";
    }

    @RequestMapping(value = "/admin/show/manage", method = RequestMethod.DELETE)
    public String deleteThisEvent(@RequestParam long evid) {
        return ss.deleteEventWithId(evid);
    }

    // notice functions ##########################################################

    // admin전용, 공지작성중 썸머노트 텍스트에어리어에 이미지 던지면 업로드하고 미리보기 할 url포함 정보 돌려주기
    @ResponseBody
    @RequestMapping(value = "/admin/file/notice", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public String noticeAttachUpload(@RequestParam("file") MultipartFile f) {
        return UpAndDownFile.uploadFile_summernote(f);
    } // 게시글 제목 누르면 상세페이지로 들어가기

    // TODO 이후 admincontroller 따로 생성해 옮길지 결정
    // admin전용, 공지작성 누르면 글쓰러 보내주기
    @RequestMapping(value = "/admin/sacnews/notice/post", method = RequestMethod.GET)
    public String toPostPage() {
        return "sacnews/new";
    }

    // TODO 이후 admincontroller 따로 생성해 옮길지 결정
    // admin전용, 내용 다 쓴 공지 등록버튼 누르면 등록해주기
    // 220129 재작성, 연관관계 재설정
    @RequestMapping(value = "/admin/sacnews/notice/post", method = RequestMethod.POST)
    public String postNewNotice(NoticeD data, MultipartHttpServletRequest htsr, Principal p) {
        return ns.postNewNotice(data, List.copyOf(htsr.getFiles("attach")), p.getName());
    }

    // TODO 이후 admincontroller 따로 생성해 옮길지 결정
    // admin전용, 공지 수정할거 다 수정하고 수정적용 버튼 누르면 할일
    @RequestMapping(value = "/admin/sacnews/notice", method = RequestMethod.PUT)
    public String editSubmit(NoticeD data, MultipartHttpServletRequest htsr,
            @RequestParam(required = false) List<Long> dfiles,
            Principal p) {
        return ns.editNotice(data, List.copyOf(htsr.getFiles("attach")), dfiles, p.getName());
    }

    // TODO 이후 admincontroller 따로 생성해 옮길지 결정
    // admin전용, 공지 삭제하고싶을 때
    @RequestMapping(value = "/admin/sacnews/notice", method = RequestMethod.DELETE)
    public String deleteSubmit(long no) {
        // 삭제하려는 공지 번호 들고 서비스가기
        return ns.deleteNotice(no);
    }

    // TODO 이후 admincontroller 따로 생성해 옮길지 결정
    // admin전용, 보고있는 상세페이지의 공지를 수정하고싶을때
    @RequestMapping(value = "/admin/sacnews/notice", method = RequestMethod.GET)
    public String toEdit(@RequestParam long no, Model m) {
        // 보고있는 공지의 id들고 서비스로
        return ns.noticeEdit(no, m);
    }
}
