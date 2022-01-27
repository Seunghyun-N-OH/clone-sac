package com.example.sac.domain.services.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.sac.domain.entities.EventE;
import com.example.sac.domain.repositories.EventR;
import com.example.sac.domain.services.ShowS;
import com.example.sac.domain.services.functions.UpAndDownFile;
import com.example.sac.web.dtos.EventD;
import com.example.sac.web.dtos.PricingPolicyD;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ShowSI implements ShowS {

    public ShowSI(EventR er) {
        this.er = er;
    }

    private final EventR er;

    @Override
    public void registerEvent(EventD a, List<String> s, List<Integer> p, MultipartFile po, MultipartFile d) {
        List<PricingPolicyD> pricingPolicyD = new ArrayList<>();
        for (int i = 0; i < s.size(); i++) {
            pricingPolicyD.add(PricingPolicyD.builder().subject(s.get(i)).price(p.get(i)).build());
        }
        a.setPricingPolicy(pricingPolicyD);

        // #################################################################################
        // a.setPoster(eir.save(EventImageD.builder().fileName("a").filePath("b").build().toEntity()).toDto());
        // a.setDetailImage(eir.save(EventImageD.builder().fileName("a").filePath("b").build().toEntity()).toDto());
        // TODO 파일들에 대한 멤버변수를 빈값으로 안넣어주면 파일이 없을 때 500에러 발생이유 알아내기

        if (po.getOriginalFilename().isEmpty() && d.getOriginalFilename().isEmpty()) {
            // 포스터, 상세페이지 이미지 둘 다 없을 땐 그 두개 없이 그냥 빌더로 만들어서 등록
            er.save((EventE.builder()
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
                    .pricingPolicy(a.getPricingPolicy().stream().map(b -> b.toEntity()).collect(Collectors.toList()))
                    .contact(a.getContact())
                    .openDate(a.getOpenDate())
                    .finDate(a.getFinDate())
                    .openTime(a.getOpenTime())
                    .lastEntrance(a.getLastEntrance())
                    .closeTime(a.getCloseTime())
                    .eventTime(a.getEventTime())
                    .runningTime(a.getRunningTime())
                    .build()));
        } else if (!po.getOriginalFilename().isEmpty() &&
                d.getOriginalFilename().isEmpty()) {
            // 포스터만 있으면, 포스터는 가져가고 상세페이지 부분만 비워서 등록
            er.save(EventE.builder()
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
                    .pricingPolicy(a.getPricingPolicy().stream().map(b -> b.toEntity()).collect(Collectors.toList()))
                    .poster(UpAndDownFile.upEventImage(po).toEntity())
                    .contact(a.getContact())
                    .openDate(a.getOpenDate())
                    .finDate(a.getFinDate())
                    .openTime(a.getOpenTime())
                    .lastEntrance(a.getLastEntrance())
                    .closeTime(a.getCloseTime())
                    .eventTime(a.getEventTime())
                    .runningTime(a.getRunningTime())
                    .build());
        } else if (po.getOriginalFilename().isEmpty() &&
                !d.getOriginalFilename().isEmpty()) {
            // 상세페이지만 있으면..
            er.save(EventE.builder()
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
                    .pricingPolicy(a.getPricingPolicy().stream().map(b -> b.toEntity()).collect(Collectors.toList()))
                    .detailImage(UpAndDownFile.upEventImage(d).toEntity())
                    .contact(a.getContact())
                    .openDate(a.getOpenDate())
                    .finDate(a.getFinDate())
                    .openTime(a.getOpenTime())
                    .lastEntrance(a.getLastEntrance())
                    .closeTime(a.getCloseTime())
                    .eventTime(a.getEventTime())
                    .runningTime(a.getRunningTime())
                    .build());
        } else if (!po.getOriginalFilename().isEmpty() &&
                !d.getOriginalFilename().isEmpty()) {
            // 파일 둘다 있으면..
            er.save(EventE.builder()
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
                    .pricingPolicy(a.getPricingPolicy().stream().map(b -> b.toEntity()).collect(Collectors.toList()))
                    .poster(UpAndDownFile.upEventImage(po).toEntity())
                    .detailImage(UpAndDownFile.upEventImage(d).toEntity())
                    .contact(a.getContact())
                    .openDate(a.getOpenDate())
                    .finDate(a.getFinDate())
                    .openTime(a.getOpenTime())
                    .lastEntrance(a.getLastEntrance())
                    .closeTime(a.getCloseTime())
                    .eventTime(a.getEventTime())
                    .runningTime(a.getRunningTime())
                    .build());
        }
    }

    @Override
    public void getShowList(Model m) {
        List<EventE> raw = er.findAll();
        if (raw.isEmpty()) {
        } else {
            m.addAttribute("shows", raw.stream().map(a -> a.toLDto()).collect(Collectors.toList()));
        }
    }
}
