package com.example.git_priv.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JH_DTO {
    private String id;
    private String name;
    private String desc;
    private String[] areas;
    private String[] codes;

    public String getAreasString(){
        String str = "";
        for(String area : areas){
            str += area + " ";
        }
        return str;
    }

    public String getCodesString(){
        String str = "";
        for(String code : codes){
            str += code + " ";
        }
        return str;
    }

    @Override
    public String toString(){
        return String.format("japan heritage] ID : %s \n이름 : %s \n 지역 : %s \n지역 코드 : %s \n설명 : %s", id, name, getAreasString(), getCodesString(), desc );
    }
}
