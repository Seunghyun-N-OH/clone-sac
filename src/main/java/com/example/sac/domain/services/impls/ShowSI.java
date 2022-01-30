package com.example.sac.domain.services.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.example.sac.domain.entities.EventE;
import com.example.sac.domain.entities.PricingPolicy;
import com.example.sac.domain.repositories.EventDetailImgR;
import com.example.sac.domain.repositories.EventPosterR;
import com.example.sac.domain.repositories.EventR;
import com.example.sac.domain.repositories.PricingPolicyR;
import com.example.sac.domain.services.ShowS;
import com.example.sac.web.dtos.EventD;
import com.example.sac.web.dtos.EventLD;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class ShowSI implements ShowS {

    public ShowSI(EventR er, PricingPolicyR ppr, EventPosterR epr, EventDetailImgR edir) {
        this.er = er;
        this.ppr = ppr;
        this.epr = epr;
        this.edir = edir;
    }

    private final EventR er;
    private final PricingPolicyR ppr;
    private final EventPosterR epr;
    private final EventDetailImgR edir;

    @Override
    public String getShowList(Model m) {

        List<EventE> raw = er.findAll();
        if (!raw.isEmpty()) {
            List<EventLD> allLists = raw.stream().map(a -> a.toListDto()).collect(Collectors.toList());
            m.addAttribute("shows", allLists);
        }
        return "show/list";
    }

    @Override
    @Transactional
    public String getShowDetail(long eventId, Model m, RedirectAttributes ra) {
        Optional<EventE> raw = er.findById(eventId);
        if (raw.isPresent()) {
            EventD data = raw.get().toDto();
            m.addAttribute("event", data);
            return "show/detail";
        } else {
            ra.addFlashAttribute("error", "행사 정보가 존재하지 않습니다");
            return "redirect:/show/show_list";
        }
        // Optional<EventE> raw = er.findById(eventId);
        // if (raw.isEmpty()) {
        // ra.addFlashAttribute("error", "행사 정보가 존재하지 않습니다.");
        // return "redirect:/show/show_list";
        // } else {
        // System.out.println(raw.get().toDto().toString()); // TODO delete after
        // implementation
        // m.addAttribute("event", raw.get().toDto());
        // return "show/detail";
        // }
    }

    @Override
    public void registerEvent(EventD a, List<String> s, List<Integer> p, MultipartFile po, MultipartFile d) {

        // 파일저장 - 포스터
        // 파일저장 - 상세페이지
        // 행사정보 자체 저장
        // 행사정보에 가격정책 추가+저장
        // TODO 2201310353 : notice 프로세스 복습하다가 끔

        List<PricingPolicy> prices = new ArrayList<>();
        for (int i = 0; i < s.size(); i++) {
            prices.add(PricingPolicy.builder().subject(s.get(i)).price(p.get(i)).build());
        }
        for (PricingPolicy a : prices) {

        }

        // List<PricingPolicy> pricingPolicyD = new ArrayList<>();
        // for (int i = 0; i < s.size(); i++) {
        // pricingPolicyD.add(PricingPolicy.builder().subject(s.get(i)).price(p.get(i)).build());
        // }
        // a.setPricingPolicy(pricingPolicyD);
        // if (!po.isEmpty())
        // a.setPoster(UpAndDownFile.upEventImage(po));
        // if (!d.isEmpty())
        // a.setDetailImage(UpAndDownFile.upEventImage(d));
        // long no = er.save(a.toEntity()).getId();

        // for (PricingPolicy ee : pricingPolicyD) {
        // ppr.save(PricingPolicy.builder().price(ee.getPrice()).subject(ee.getSubject())
        // .event(EventE.builder().id(no).build()).build());
        // }
    }

}
