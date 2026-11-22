package com.example.git_priv.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class tl_DTO {
    private String key;
    private String name;
    private String desc;

    @Override
    public String toString(){
        return String.format("%s %s %s", key, name, desc);
    }
}
