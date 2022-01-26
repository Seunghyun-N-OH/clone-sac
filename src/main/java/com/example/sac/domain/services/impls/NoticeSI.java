package com.example.sac.domain.services.impls;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.example.sac.SecuritiyThings.repositories.NoticeR;
import com.example.sac.domain.entities.AttachedFile;
import com.example.sac.domain.entities.Notice;
import com.example.sac.domain.repositories.AttachedFileR;
import com.example.sac.domain.services.NoticeS;
import com.example.sac.domain.services.functions.UpAndDownFile;
import com.example.sac.web.dtos.AttachedFileD;
import com.example.sac.web.dtos.NoticeD;
import com.example.sac.web.dtos.NoticeLD;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

// 주석 업데이트 220126

@Service
public class NoticeSI implements NoticeS {

    public NoticeSI(NoticeR nr, AttachedFileR afr) {
        this.nr = nr;
        this.afr = afr;
    }

    // 공지사항들중에 중요한애들은 카테고리분류 이런거 상관없이 항상 맨위에 보여질 것이라
    // 반복적으로 쓰이니 따로 함수작성
    public List<NoticeLD> getImportantList() {
        List<Notice> importantTmp = nr.findByImportantOrderByEffectiveDateBDesc('y');
        if (!importantTmp.isEmpty()) {
            return importantTmp.stream().map(a -> a.toDto().toListDto()).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private final AttachedFileR afr;
    private final NoticeR nr;

    // 공지사항 새로 작성
    @Override
    public void postNewNotice(NoticeD data, Set<MultipartFile> attachedFile) {
        Notice e = (nr.save(data.toEntity())); // 우선 첨부파일 제외 공지사항내용 save, 저장된 entity 리턴받아두고
        for (MultipartFile ab : attachedFile) { // response에서 가져온 attachedFile 을 하나씩 까봐서
            if (!ab.getOriginalFilename().isEmpty()) { // null상태인지 확인 (첨부파일 없으면 null값인, 길이1 List)
                AttachedFileD a = UpAndDownFile.uploadFile(ab, e);
                // null 아니면 그파일 업로드하고, DTO 돌려받음
                afr.save(a.toEntity());
                // 돌려받은거 entity로 바꿔서, attachment 테이블에 save
            }
        }
    }

    // 처음 목록페이지 들어가면 일단 다불러오기 근데 다 안불러오고 목록 보여줄때 필요한 정보만 있는 미니dto로 가져갈거
    @Override
    public String loadList(Model m) {

        // 그중에 중요공지는 일단 불러다가 따로 모델에 넣어두고
        if (getImportantList() != null) {
            m.addAttribute("importantList", getImportantList());
        }

        // 이제 안중요한애들 불러서 모델에 넣어줌
        List<Notice> normalNotice = nr.findByImportantOrderByEffectiveDateBDesc('n');
        if (!normalNotice.isEmpty()) {
            m.addAttribute("noticelist",
                    normalNotice.stream().map(a -> a.toDto().toListDto()).collect(Collectors.toList()));
        }

        // 이 정보들 보여줄 형식이 있는 html파일 리턴
        return "sacnews/listPart";
    }

    // 목록에서 가져온 공지사항의 id번호로 모든정보가진 dto 가져가기
    @Override
    public String noticeDetail(long notice, Model m) {
        // 상세페이지에서 다음글 이동을 위한 다음글 dto, 있으면가져감
        Optional<Notice> next = nr.findById(notice + 1);
        if (next.isPresent()) {
            m.addAttribute("next", next.get().toDto().toListDto());
        }
        // 상세페이지에서 이전글 이동을 위한 이전글 dto, 이거도 있으면 가져감
        Optional<Notice> previous = nr.findById(notice - 1);
        if (previous.isPresent()) {
            m.addAttribute("previous", previous.get().toDto().toListDto());
        }

        // 지금 보려는 글의 모든정보 불러다가 모델에 넣어서 페이지이동
        Optional<Notice> raw = nr.findById(notice);
        if (raw.isPresent()) { // 혹시나 페이지 이동하는사이 글 삭제되어서 없으면 다시 목록으로
            m.addAttribute("notice", raw.get().toDto());
            List<AttachedFile> attachments = afr.findByNoticeNo(notice);
            if (!attachments.isEmpty()) {
                m.addAttribute("attachedFiles", attachments.stream().map(a -> a.toDto()).collect(Collectors.toList()));
            }
            return "sacnews/detail";
        } else {
            return "redirect:/sacnews/notice";
        }
    }

    // 카테고리이름 누르면, 그 카테고리에 맞는글만 보여주기
    @Override
    public String loadTargetedList(Model m, String cat) {

        // 일단 중요한 애들 있는지 먼저 봐서 줏어오고
        if (getImportantList() != null) {
            m.addAttribute("importantList", getImportantList());
        }

        // 요청받은 카테고리에 해당하는 글만 골라서 가져가기
        if (!nr.findByCategoryOrderByEffectiveDateBDesc(cat).isEmpty()) {
            List<Notice> raw = nr.findByCategoryOrderByEffectiveDateBDesc(cat);
            m.addAttribute("noticelist", raw.stream().map(a -> a.toDto().toListDto()).collect(Collectors.toList()));
        }

        return "sacnews/listPart";
    }

    // 검색하려는 범주, 키워드에 맞게 리스트 가져가기
    @Override
    public String searchNotice(String target, String key, Model m) {

        // 여기서도 중요한애들 먼저 가져가고
        if (getImportantList() != null) {
            m.addAttribute("importantList", getImportantList());
        }

        // 검색한 범주가 전체다인지 제목인지 내용인지에 따라 거기에 맞는 목록 가져가기
        if (target.equals("all")) {
            m.addAttribute("noticelist",
                    nr.findByContentContainingOrTitleContainingOrderByEffectiveDateBDesc(key, key));
            // 전체범위로 찾았으면 내용/제목 어디든 들어있으면 가져감
            return "sacnews/listPart";
        } else if (target.equals("content")) {
            m.addAttribute("noticelist", nr.findByContentContainingOrderByEffectiveDateBDesc(key));
            // 여기선 내용만
            return "sacnews/listPart";
        } else if (target.equals("title")) {
            m.addAttribute("noticelist", nr.findByTitleContainingOrderByEffectiveDateBDesc(key));
            // 여기선 제목만
            return "sacnews/listPart";
        } else {
            // 선택지가 3개뿐인데 다른게 들어올리가없지
            return null;
        }
    }

    // 공지 수정하러 갈때 기존정보 불러가기
    // TODO 파일도 수정해야하니 같이 가져가기
    @Override
    public String noticeEdit(long targetNo, Model m) {
        Optional<Notice> raw = nr.findById(targetNo);
        if (raw.isPresent()) { // 이거도 혹시나 불러오는데 그사이 '다른admin'이 삭제할까봐 체크하고
            m.addAttribute("notice", raw.get().toDto());
            // 가져감
            return "sacnews/edit";
        } else {
            return "redirect:/sacnews/notice";
        }
    }

    // 내용 바꿔서 이제 수정할라그러면
    @Override
    public void editNotice(NoticeD data) {
        // 바뀐 정보가 포함된 DTO를 entity로 바꿔서 save()함수 사용해 update
        // TODO 파일수정 포함시키기
        nr.save(data.toEntity());
    }

    // 공지 삭제하려그러면
    @Override
    public void deleteNotice(long no) {
        if (!afr.findByNoticeNo(no).isEmpty()) { // 그 공지에 첨부파일 있나 확인하고
            UpAndDownFile.deleteFiles(afr.findByNoticeNo(no));
            // 있으면 그파일들 먼저 다 삭제하고
            afr.deleteByNoticeNo(no);
            // 등록된 파일정보도 삭제해버림(그럼 게시글도 삭제됨)
        } else {
            nr.deleteById(no);
            // 파일 없으면 게시글만 삭제
        }
    }

    // 첨부파일 받고싶어하면
    @Override
    public void downService(long nn, long fn, HttpServletResponse response) throws FileNotFoundException, IOException {
        // 어떤 공지의 어떤 파일인지 찾아와서
        Optional<AttachedFile> raw = afr.findByFnoAndNoticeNo(fn, nn);
        if (raw.isPresent()) {
            UpAndDownFile.downFile(response, raw.get());
            // 그 파일 내려주기
        }
    }

}
