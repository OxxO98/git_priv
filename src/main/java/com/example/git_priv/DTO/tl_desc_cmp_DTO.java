package com.example.git_priv.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class tl_desc_cmp_DTO {
    private String qid;
    private String desc;

    @Override
    public String toString(){
        return String.format("TL_CMP] %s %s", qid, desc);
    }
}
