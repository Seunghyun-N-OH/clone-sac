package com.example.sac.domain.services.impls;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.sac.SecuritiyThings.entities.Notice;
import com.example.sac.SecuritiyThings.repositories.NoticeR;
import com.example.sac.domain.services.NoticeS;
import com.example.sac.web.dtos.NoticeD;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class NoticeSI implements NoticeS {

    public NoticeSI(NoticeR nr) {
        this.nr = nr;
    }

    private final NoticeR nr;

    @Override
    public void postNewNotice(NoticeD data) {
        nr.save(data.toEntity());
    }

    // get important notice
    public List<NoticeD> getImportantList() {
        List<Notice> importantTmp = nr.findByImportantOrderByEffectiveDateBDesc('y');
        if (!importantTmp.isEmpty()) {
            return importantTmp.stream().map(a -> a.toDto()).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public String loadList(Model m, String category) { // list for main page of notice section (find All notices)

        if (getImportantList() != null) {
            m.addAttribute("importantList", getImportantList());
        }

        List<Notice> normalNotice = nr.findByImportantOrderByEffectiveDateBDesc('n');
        if (!normalNotice.isEmpty()) {
            m.addAttribute("noticelist", normalNotice.stream().map(a -> a.toDto()).collect(Collectors.toList()));
            // find&collect not-important notice(s) to List, send the list as 'noticelist'
        }

        System.out.println("service::loadList() executed");
        return "sacnews/listPart";
    }

    @Override
    public String noticeDetail(long notice, Model m) {
        // next notice no, next notice title
        Optional<Notice> next = nr.findById(notice + 1);
        if (next.isPresent()) {
            m.addAttribute("next", next.get().toDto());
        }
        // previous notice no, previous notice title
        Optional<Notice> previous = nr.findById(notice - 1);
        if (previous.isPresent()) {
            m.addAttribute("previous", previous.get().toDto());
        }

        Optional<Notice> raw = nr.findById(notice);
        if (raw.isPresent()) {
            m.addAttribute("notice", raw.get().toDto());
            return "sacnews/detail";
        } else {
            // in case the notice has deleted(in the moment between click and loading)
            return "redirect:/sacnews/notice";
        }
    }

    @Override
    public String loadTargetedList(Model m, String cat) {

        if (getImportantList() != null) {
            m.addAttribute("importantList", getImportantList());
        }

        if (!nr.findByCategoryOrderByEffectiveDateBDesc(cat).isEmpty()) {
            List<Notice> raw = nr.findByCategoryOrderByEffectiveDateBDesc(cat);
            m.addAttribute("noticelist", raw.stream().map(a -> a.toDto()).collect(Collectors.toList()));
        }

        return "sacnews/listPart";
    }

    @Override
    public String searchNotice(String target, String key, Model m) {
        if (getImportantList() != null) {
            m.addAttribute("importantList", getImportantList());
        }

        if (target.equals("all")) {
            m.addAttribute("noticelist",
                    nr.findByContentContainingOrTitleContainingOrderByEffectiveDateBDesc(key, key));
            return "sacnews/listPart";
        } else if (target.equals("content")) {
            m.addAttribute("noticelist", nr.findByContentContainingOrderByEffectiveDateBDesc(key));
            return "sacnews/listPart";
        } else if (target.equals("title")) {
            m.addAttribute("noticelist", nr.findByTitleContainingOrderByEffectiveDateBDesc(key));
            return "sacnews/listPart";
        } else {
            return null;
        }
    }

    @Override
    public String noticeEdit(long targetNo, Model m) {
        Optional<Notice> raw = nr.findById(targetNo);
        if (raw.isPresent()) {
            m.addAttribute("notice", raw.get().toDto());
            return "sacnews/edit";
        } else {
            // in case the notice has deleted(in the moment between click and loading)
            return "redirect:/sacnews/notice";
        }
    }

    @Override
    public void editNotice(NoticeD data) {
        nr.save(data.toEntity());
    }

    @Override
    public void deleteNotice(long no) {
        nr.deleteById(no);
    }

}
