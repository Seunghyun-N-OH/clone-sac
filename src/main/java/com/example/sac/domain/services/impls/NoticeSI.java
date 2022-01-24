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

    @Override
    public String loadList(Model m) { // list for main page of notice section (find All notices)
        System.out.println("service::loadList() catch"); // TODO delete after implementation
        List<Notice> importantTmp = nr.findByImportantOrderByNoDesc('y');
        if (!importantTmp.isEmpty()) {
            m.addAttribute("importantList", importantTmp.stream().map(a -> a.toDto()).collect(Collectors.toList()));
        } // find&collect important notice(s) to List, send the list as 'importantList'

        List<Notice> normalNotice = nr.findByImportantOrderByNoDesc('n');
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

}
