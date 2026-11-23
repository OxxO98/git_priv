package com.example.git_priv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelInfo_DTO {
    private int info_no;
    private String info_year;
    private String info_region;
    private String info_assc;
    private String info_title;
    private String info_content;
    private String info_year_ko;
    private String info_region_ko;
    private String info_title_ko;
    private String info_content_ko;
}
