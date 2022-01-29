package com.example.sac.domain.services.impls;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.example.sac.SecuritiyThings.repositories.NoticeR;
import com.example.sac.domain.entities.AttachedFile;
import com.example.sac.domain.entities.NoticeE;
import com.example.sac.domain.repositories.AttachedFileR;
import com.example.sac.domain.services.NoticeS;
import com.example.sac.domain.services.functions.UpAndDownFile;
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

    private final AttachedFileR afr;
    private final NoticeR nr;

    // 처음 목록페이지 들어가면 일단 다불러오기 근데 다 안불러오고 목록 보여줄때 필요한 정보만 있는 미니dto로 가져갈거
    @Override
    public String loadList(Model m) {

        // 그중에 중요공지는 일단 불러다가 따로 모델에 넣어두고
        if (getImportantList() != null) {
            m.addAttribute("importantList", getImportantList());
        }

        // 이제 안중요한애들 불러서 모델에 넣어줌
        List<NoticeE> normalNotice = nr.findByImportantOrderByEffectiveDateBDesc('n');
        if (!normalNotice.isEmpty()) {
            m.addAttribute("noticelist",
                    normalNotice.stream().map(a -> a.toDto().toListDto()).collect(Collectors.toList()));
        }

        // 이 정보들 보여줄 형식이 있는 html파일 리턴
        return "sacnews/listPart";
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
            List<NoticeE> raw = nr.findByCategoryOrderByEffectiveDateBDesc(cat);
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
    @Transactional
    public String noticeEdit(long targetNo, Model m) {
        Optional<NoticeE> raw = nr.findById(targetNo);
        if (raw.isPresent()) { // 이거도 혹시나 불러오는데 그사이 '다른admin'이 삭제할까봐 체크하고
            NoticeE raw_with_files = raw.get();
            System.out.println(raw_with_files.getAttachment());
            m.addAttribute("notice", raw_with_files.toDto());
            // 가져감
            return "sacnews/edit";
        } else {
            return "redirect:/sacnews/notice";
        }
    }

    // TODO 이거 하던중..
    // 내용 바꿔서 이제 수정할라그러면
    @Override
    @Transactional
    public String editNotice(NoticeD d, List<MultipartFile> f, List<Long> df, String n) {
        Optional<NoticeE> on = nr.findById(d.getNo());
        if (on.isPresent()) {
            NoticeE data = on.get();
            System.out.println("before file handling : " + data.getAttachment());

            for (AttachedFile file : UpAndDownFile.uploadMultipleFiles(f)) {
                if (!file.getFileName().isEmpty())
                    data.addFile(file);
            }

            for (long target : df) {
                for (long i = 0; i < data.getAttachment().size(); i++) {
                    if (data.getAttachment().get((int) i).getFno() == target) {
                        afr.delete(data.getAttachment().get((int) i));
                    }
                }
            }
            System.out.println("after file handling : " + data.getAttachment());

            NoticeE updated = NoticeE.builder()
                    .no(data.getNo())
                    .category(d.getCategory())
                    .drafter(n)
                    .important(d.getImportant())
                    .title(d.getTitle())
                    .content(d.getContent())
                    .effectiveDateB(d.getEffectiveDateB())
                    .effectiveDateE(d.getEffectiveDateE())
                    .attachment(data.getAttachment())
                    .build();

            nr.save(updated);

            return "redirect:/sacnews/notice/" + d.getNo();
        } else {
            return "redirect:/sacnews/notice";
        }

    }

    // 공지 삭제하려그러면
    @Override
    public String deleteNotice(long no) {
        nr.deleteById(no);
        return "redirect:/sacnews/notice";
    }

    // #####################################################################################################

    // 220129 연관관계 재설정 후 코드 재작성완료
    @Override
    public String postNewNotice(NoticeD data, List<MultipartFile> attachedFile, String drafter) {
        NoticeE newPost = NoticeE.builder()
                .category(data.getCategory())
                .drafter(drafter)
                .important(data.getImportant())
                .views(data.getViews())
                .title(data.getTitle())
                .content(data.getContent())
                .effectiveDateB(data.getEffectiveDateB())
                .effectiveDateE(data.getEffectiveDateE())
                .cTime(data.getCTime())
                .eTime(data.getETime())
                .build();
        for (AttachedFile a : UpAndDownFile.uploadMultipleFiles(attachedFile)) {
            newPost.addFile(a);
        }
        System.out.println();
        nr.save(newPost);

        return "redirect:/sacnews/notice";
    }

    // 220129 연관관계 재설정 후 코드 재작성완료
    // 목록에서 가져온 공지사항의 id번호로 모든정보가진 dto 가져가기
    @Override
    @Transactional
    public String noticeDetail(long notice, Model m) {
        Optional<NoticeE> raw = nr.findById(notice);
        if (raw.isPresent()) {
            NoticeE data = raw.get();
            System.out.println(data.getAttachment()); // Lazy 로딩 select 쿼리날리기
            m.addAttribute("notice", raw.get().toDto());
            return "sacnews/detail";
        } else {
            return "redirect:/sacnews/notice";
        }
    }

    // 220129 코드 재작성 불필요, 계속사용
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

    // 220129 코드 재작성 불필요, 계속사용
    // 공지사항들중에 중요한애들은 카테고리분류 이런거 상관없이 항상 맨위에 보여질 것이라
    // 반복적으로 쓰이니 따로 함수작성
    public List<NoticeLD> getImportantList() {
        List<NoticeE> importantTmp = nr.findByImportantOrderByEffectiveDateBDesc('y');
        if (!importantTmp.isEmpty()) {
            return importantTmp.stream().map(a -> a.toDto().toListDto()).collect(Collectors.toList());
        } else {
            return null;
        }
    }

}
