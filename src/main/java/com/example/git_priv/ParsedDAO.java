package com.example.git_priv;

import com.example.git_priv.DTO.*;

import java.util.HashMap;
import java.util.Map;

public class ParsedDAO {

    private static final ParsedDAO dao = new ParsedDAO();

    public Map<String, String> parsedData = new HashMap<>();
    public Map<String, String> paresdTlCompleteData = new HashMap<>();

    private ParsedDAO(){ }

    public static ParsedDAO getInstance(){
        return dao;
    }

    public void joinWithBI(Map<String, tl_DTO> parsedTL, Map<String, BI_DTO> parsedBI ){
        for(Map.Entry<String, BI_DTO> entry : parsedBI.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (parsedTL.containsKey(key)) {
                String str = parsedTL.get(key.toString()).getDesc();
                value += str != null ? str : "";
            }
            parsedData.put(key, value);
        }
    }

    public void joinWithMG( Map<String, tl_DTO> parsedTL, Map<String, MG_DTO> parsedMG ){
        for(Map.Entry<String, MG_DTO> entry : parsedMG.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (parsedTL.containsKey(key)) {
                String str = parsedTL.get(key.toString()).getDesc();
                value += str != null ? str : "";
            }
            parsedData.put(key, value);
        }
    }

    public void joinWithJH( Map<String, tl_DTO> parsedTL, Map<String, JH_DTO> parsedJH ){
        for(Map.Entry<String, JH_DTO> entry : parsedJH.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (parsedTL.containsKey(key)) {
                String str = parsedTL.get(key.toString()).getDesc();
                value += str != null ? str : "";
            }
            parsedData.put(key, value);
        }
    }

    public void joinWithUJ( Map<String, tl_DTO> parsedTL, Map<String, UJ_DTO> parsedUJ){
        for(Map.Entry<String, UJ_DTO> entry : parsedUJ.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (parsedTL.containsKey(key)) {
                String str = parsedTL.get(key.toString()).getDesc();
                value += str != null ? str : "";
            }
            parsedData.put(key, value);
        }
    }

    public void joinRest( Map<String, tl_DTO> parsedTL ){
        //tl rest (meti_densan으로 추정)
        for(Map.Entry<String, tl_DTO> entry : parsedTL.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (!parsedData.containsKey(key)) {
                parsedData.put(key, value);
            }
        }
    }

    public void reviseTLComplete( Map<String, tl_desc_cmp_DTO> parseTL_cmp ){
        for(Map.Entry<String, tl_desc_cmp_DTO> entry : parseTL_cmp.entrySet() ){
            String key = "cmp" + entry.getKey();
            String value = entry.getValue().toString();
            paresdTlCompleteData.put(key, value);
        }
    }

}
