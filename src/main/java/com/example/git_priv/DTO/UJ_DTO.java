package com.example.git_priv.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UJ_DTO {
    private String qid;
    private String name;
    private String desc;

    @Override
    public String toString(){
        return String.format("MG_DTO] %s %s %s", qid, name, desc );
    }
}
