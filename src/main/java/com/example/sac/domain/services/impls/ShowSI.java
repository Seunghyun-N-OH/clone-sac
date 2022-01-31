package com.example.sac.domain.services.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.example.sac.domain.entities.EventDetailImg;
import com.example.sac.domain.entities.EventE;
import com.example.sac.domain.entities.EventPoster;
import com.example.sac.domain.entities.PricingPolicy;
import com.example.sac.domain.repositories.EventDetailImgR;
import com.example.sac.domain.repositories.EventPosterR;
import com.example.sac.domain.repositories.EventR;
import com.example.sac.domain.repositories.PricingPolicyR;
import com.example.sac.domain.services.ShowS;
import com.example.sac.domain.services.functions.UpAndDownFile;
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
    public void registerEvent(EventD a, List<String> s, List<Integer> p, MultipartFile po, List<MultipartFile> d) {

        // 파일저장 - 포스터
        EventPoster poster = UpAndDownFile.upEventPoster(po);

        // 행사정보 자체 저장
        EventE inData = EventE.builder()
                .eventGroup(a.getEventGroup())
                .venue1(a.getVenue1())
                .venue2(a.getVenue2())
                .venue3(a.getVenue3())
                .sacPlanned(a.getSacPlanned())
                .eventTitle(a.getEventTitle())
                .host(a.getHost())
                .organizer(a.getOrganizer())
                .sponsor(a.getSponsor())
                .requiredAge(a.getRequiredAge())
                .onSale(a.getOnSale())
                .poster(poster)
                .contact(a.getContact())
                .openDate(a.getOpenDate())
                .lastEntrance(a.getLastEntrance())
                .closeTime(a.getCloseTime())
                .eventTime(a.getEventTime())
                .runningTime(a.getRunningTime())
                .build();
        // 파일저장 - 상세페이지
        List<EventDetailImg> details = UpAndDownFile.upEventDetailImages(d);
        for (EventDetailImg de : details) {
            edir.save(de);
            inData.addEventDetailImg(de);
        }
        // 행사정보에 가격정책 추가+저장
        List<PricingPolicy> pricingPolicyD = new ArrayList<>();
        for (int i = 0; i < s.size(); i++) {
            pricingPolicyD.add(PricingPolicy.builder().subject(s.get(i)).price(p.get(i)).build());
        }
        EventE savedEvent = er.save(inData);
        for (PricingPolicy pp : pricingPolicyD) {
            savedEvent.addPricingPolicy(pp);
            ppr.save(pp);
        }

        System.out.println(savedEvent.toString());
    }

    @Override
    @Transactional
    public String getShowDetail(long eventId, Model m, RedirectAttributes ra) {
        Optional<EventE> raw = er.findById(eventId);
        if (raw.isPresent()) {
            EventE data = raw.get();
            System.out.println(data.getDetail_img().stream().toString());
            System.out.println(data.getPoster().toString());
            System.out.println(data.getPricingPolicy());
            System.out.println(data.getEventTime());
            System.out.println(data.toString());
            m.addAttribute("event", data.toDto());
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

}
