package com.example.sac.web.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeLD {
    private long no;
    private String category;
    private String title;
    private LocalDate effectiveDateB;
    private LocalDate effectiveDateE;
}
