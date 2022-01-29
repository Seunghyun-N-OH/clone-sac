package com.example.sac.domain.services.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.example.sac.domain.entities.EventE;
import com.example.sac.domain.entities.PricingPolicy;
import com.example.sac.domain.repositories.EventR;
import com.example.sac.domain.repositories.PricingPolicyR;
import com.example.sac.domain.services.ShowS;
import com.example.sac.domain.services.functions.UpAndDownFile;
import com.example.sac.web.dtos.EventD;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class ShowSI implements ShowS {

    public ShowSI(EventR er, PricingPolicyR ppr) {
        this.er = er;
        this.ppr = ppr;
    }

    private final EventR er;
    private final PricingPolicyR ppr;

    @Override
    public void registerEvent(EventD a, List<String> s, List<Integer> p, MultipartFile po, MultipartFile d) {
        List<PricingPolicy> pricingPolicyD = new ArrayList<>();
        for (int i = 0; i < s.size(); i++) {
            pricingPolicyD.add(PricingPolicy.builder().subject(s.get(i)).price(p.get(i)).build());
        }
        a.setPricingPolicy(pricingPolicyD);
        if (!po.isEmpty())
            a.setPoster(UpAndDownFile.upEventImage(po));
        if (!d.isEmpty())
            a.setDetailImage(UpAndDownFile.upEventImage(d));
        long no = er.save(a.toEntity()).getId();

        for (PricingPolicy ee : pricingPolicyD) {
            ppr.save(PricingPolicy.builder().price(ee.getPrice()).subject(ee.getSubject())
                    .event(EventE.builder().id(no).build()).build());
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

    @Override
    @Transactional
    public String getShowDetail(long eventId, Model m, RedirectAttributes ra) {
        Optional<EventE> raw = er.findById(eventId);
        if (raw.isEmpty()) {
            ra.addFlashAttribute("error", "행사 정보가 존재하지 않습니다.");
            return "redirect:/show/show_list";
        } else {
            System.out.println(raw.get().toDto().toString()); // TODO delete after implementation
            m.addAttribute("event", raw.get().toDto());
            return "show/detail";
        }
    }
}
