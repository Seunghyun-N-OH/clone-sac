package com.example.sac.web.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class NoticeLD {
    private long no;
    private String category;
    private String title;
    private LocalDate effectiveDateB;
    private LocalDate effectiveDateE;
}
