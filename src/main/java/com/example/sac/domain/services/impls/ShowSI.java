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
    @Transactional
    public String getShowList(Model m) {

        List<EventE> raw = er.findAll();
        for (EventE a : raw)
            System.out.println(a.getEventTime());

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
                .finDate(a.getFinDate())
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
            if (data.getPoster() != null)
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
    }

    @Override
    @Transactional
    public String deleteEventWithId(long evid) {
        Optional<EventE> raw = er.findById(evid);
        if (raw.isPresent()) {
            for (PricingPolicy a : raw.get().getPricingPolicy()) {
                ppr.deleteById(a.getPriceId());
            }
            if (raw.get().getPoster() != null) {
                UpAndDownFile.deletePosterFile(raw.get().getPoster());
                epr.deleteById(raw.get().getPoster().getImageId());
            }
            for (EventDetailImg a : raw.get().getDetail_img()) {
                UpAndDownFile.deleteDetailImage(a);
                edir.deleteById(a.getId());
            }
            er.deleteById(evid);
            return "redirect:/show";
        } else {
            return "redirect:/show/" + evid;
        }
    }

    @Override
    @Transactional
    public String saveEditedEvent(EventD a, List<String> subject, List<Integer> price, MultipartFile poster_file,
            List<MultipartFile> copyOf, String deletePoster, List<Long> deleteDetails) {
        System.out.println("Service : " + a);
        Optional<EventE> before = er.findById(a.getId());
        if (!before.isPresent())
            return "redirect:/show";
        else {
            if (deletePoster != null) {
                epr.deleteById(before.get().getPoster().getImageId());
                before.get().removePoster();
            }
            if (deleteDetails != null) {
                for (Long detailNo : deleteDetails) {
                    UpAndDownFile.deleteDetailImage(edir.findById(detailNo).get());
                    before.get().getDetail_img().remove(edir.findById(detailNo).get());
                    edir.deleteById(detailNo);
                }
            }

            for (PricingPolicy ab : before.get().getPricingPolicy()) {
                ab.setEvent(null);
                ppr.delete(ab);
            }

            EventE newData;
            List<PricingPolicy> pricingPolicyD = new ArrayList<>();

            if (!poster_file.getOriginalFilename().isEmpty()) {
                EventPoster newPoster = UpAndDownFile.upEventPoster(poster_file);
                newData = EventE.builder()
                        .id(a.getId())
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
                        .poster(newPoster)
                        .detail_img(before.get().getDetail_img())
                        .contact(a.getContact())
                        .openDate(a.getOpenDate())
                        .finDate(a.getFinDate())
                        .pricingPolicy(pricingPolicyD)
                        .lastEntrance(a.getLastEntrance())
                        .closeTime(a.getCloseTime())
                        .eventTime(a.getEventTime())
                        .runningTime(a.getRunningTime())
                        .build();
            } else {
                newData = EventE.builder()
                        .id(a.getId())
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
                        .poster(before.get().getPoster())
                        .detail_img(before.get().getDetail_img())
                        .contact(a.getContact())
                        .openDate(a.getOpenDate())
                        .finDate(a.getFinDate())
                        .pricingPolicy(pricingPolicyD)
                        .lastEntrance(a.getLastEntrance())
                        .closeTime(a.getCloseTime())
                        .eventTime(a.getEventTime())
                        .runningTime(a.getRunningTime())
                        .build();
            }
            List<EventDetailImg> newDetails = UpAndDownFile.upEventDetailImages(copyOf);
            if (!newDetails.isEmpty()) {
                for (EventDetailImg de : newDetails) {
                    edir.save(de);
                    newData.addEventDetailImg(de);
                }
            }

            EventE newSaved = er.save(newData);

            for (int i = 0; i < subject.size(); i++) {
                pricingPolicyD.add(PricingPolicy.builder().subject(subject.get(i)).price(price.get(i)).build());
            }

            for (PricingPolicy npp : pricingPolicyD) {
                newSaved.addPricingPolicy(npp);
                ppr.save(npp);
            }
            return "redirect:/show/" + newSaved.getId();
        }
    }

    @Override
    @Transactional
    public String getShowIndex(Model m) {
        List<EventE> raw = er.findAll();

        for (EventE a : raw) {
            System.out.println(a.getEventTime());
            if (a.getPoster() != null)
                System.out.println(a.getPoster().toString());
        }

        if (!raw.isEmpty()) {
            List<EventD> pickedList = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                pickedList.add(raw.get(i).toDto());
            }
            m.addAttribute("rotateList", pickedList);
        }
        return "/index";
    }

}
