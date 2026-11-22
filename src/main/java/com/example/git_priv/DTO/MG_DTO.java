package com.example.git_priv.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MG_DTO {
    private String id;
    private String name;
    private String desc;
    private String[] codes;

    public String getCodesString(){
        String str = "";
        for(String code : codes){
            str += code + " ";
        }
        return str;
    }
    @Override
    public String toString(){
        return String.format("MG_DTO] ID : %s \n\r이름 : %s \n\r지역코드 : %s \n\r설명 : %s\n\r", id, name, getCodesString(), desc );
    }
}
