package com.example.git_priv;

import com.example.git_priv.DTO.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonlParser {

    public static Map<String, tl_DTO> parseTL(HttpServletRequest req, String url) {

        ObjectMapper mapper = new ObjectMapper();

        try (BufferedReader br = new BufferedReader(
                new FileReader(req.getServletContext().getRealPath(url)))) {

            String line;

            Map<String, tl_DTO> tls = new HashMap<>();
            Map<String, Integer> key_map = new HashMap<>();
            while ((line = br.readLine()) != null) {

                // 빈 줄 제외
                if (line.trim().isEmpty()) {
                    continue;
                }

                // JSON 문자열을 JsonNode로 변환
                JsonNode json = mapper.readTree(line);

                String key = json.get("key").asText();
                String name = json.get("name").get("ko").asText();
                String desc = null;
                if (json.get("description") != null) {
                    desc = json.get("description").get("ko").asText();
                    desc = desc != null ? desc : "";
                }

                String trim_category = key.substring(0, key.indexOf(":"));
                String trim_key = key.substring(key.indexOf(":") + 1);
                if (!key_map.containsKey(trim_category)) {
                    key_map.put(trim_category, 1);
                } else {
                    key_map.put(trim_category, key_map.get(trim_category) + 1);
                }

                tls.put(trim_key, new tl_DTO(trim_key, name, desc));
            }

            System.out.println(key_map);
            req.setAttribute("tl_key_map", key_map);
            req.setAttribute("tl_translations", tls);

            return tls;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, tl_desc_cmp_DTO> parseTL_CMP(HttpServletRequest req, String url) {

        ObjectMapper mapper = new ObjectMapper();

        try (BufferedReader br = new BufferedReader(
                new FileReader(req.getServletContext().getRealPath(url)))) {

            String line;

            Map<String, tl_desc_cmp_DTO> tls = new HashMap<>();

            while ((line = br.readLine()) != null) {

                // 빈 줄 제외
                if (line.trim().isEmpty()) {
                    continue;
                }

                // JSON 문자열을 JsonNode로 변환
                JsonNode json = mapper.readTree(line);

                String qid = json.get("qid").asText();
                String desc = null;
                if (json.get("descriptions") != null) {
                    desc = json.get("descriptions").get("ko").asText();
                    desc = desc != null ? desc : "";
                }

                tls.put(qid, new tl_desc_cmp_DTO(qid, desc));

            }

            return tls;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, BI_DTO> parseBI(HttpServletRequest req, String url) {

        ObjectMapper mapper = new ObjectMapper();
        try {

            File jsonString = new File(req.getServletContext().getRealPath(url));

            JsonNode json = mapper.readTree(jsonString);

            JsonNode records = json.get("records");

            Map<String, BI_DTO> bis = new HashMap<>();
            for (JsonNode record : records) {
                String qid = record.get("qid").asText();
//                String designation_qid = record.get("designation_qid").asText();
                String name = record.get("name_ja").asText();
                String desc = record.get("description_ja").asText();
                desc = desc.equals("null") ? desc : "";

                bis.put(qid, new BI_DTO(qid, name, desc));
            }

            req.setAttribute("bi_items", bis);

            return bis;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static Map<String, MG_DTO> parseMG(HttpServletRequest req, String url) {

        ObjectMapper mapper = new ObjectMapper();
        try {

            File jsonString = new File(req.getServletContext().getRealPath(url));

            JsonNode json = mapper.readTree(jsonString);

            JsonNode records = json.get("records");

            Map<String, MG_DTO> mgs = new HashMap<>();
            for (int i = 0; i < records.size(); i++) {
                JsonNode record = records.get(i);

                String id = String.format("%03d", i);
                String name = record.get("name_ja").asText();
                String desc = record.get("characteristics_ja").asText();
                desc = desc.equals("null") ? desc : "";

                JsonNode codeNodes = record.get("prefecture_codes");
                String[] codes = new String[codeNodes.size()];
                for( int j = 0; j < codeNodes.size(); j++ ){
                    if( codeNodes.get(j) != null) {
                        codes[j] = codeNodes.get(j).asText();
                    }
                }

                mgs.put(id, new MG_DTO(id, name, desc, codes));
            }

            req.setAttribute("mg_items", mgs);

            return mgs;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


    public static Map<String, JH_DTO> parseJH(HttpServletRequest req, String url) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            File jsonString = new File(req.getServletContext().getRealPath(url));

            JsonNode json = mapper.readTree(jsonString);

            JsonNode records = json.get("records");

            Map<String, JH_DTO> jhs = new HashMap<>();
            for (int i = 0; i < records.size(); i++) {
                JsonNode record = records.get(i);

                String id = record.get("story_id").asText();
                String name = record.get("title_ja").asText();
                String desc = record.get("body_ja").asText();
                desc = desc.equals("null") ? desc : "";

                String[] areaTexts = record.get("related_areas_text").asText().split("・");
                String[] codes = new String[areaTexts.length];
                JsonNode codeNodes = record.get("prefecture_codes");
                for (int j = 0; j < areaTexts.length; j++) {
                    int _index = areaTexts[j].indexOf("（");
                    if( _index != -1){
                        areaTexts[j] = areaTexts[j].substring( 0, _index );
                    }
                    if( codeNodes.get(j) != null){
                        codes[j] = codeNodes.get(j).asText();
                    }
                }

                jhs.put(id, new JH_DTO(id, name, desc, areaTexts, codes));
            }

            req.setAttribute("jh_items", jhs);

            return jhs;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, UJ_DTO> parseUJ(HttpServletRequest req, String url) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            File jsonString = new File(req.getServletContext().getRealPath(url));

            JsonNode json = mapper.readTree(jsonString);

            JsonNode records = json.get("records");

            Map<String, UJ_DTO> ujs = new HashMap<>();
            for (int i = 0; i < records.size(); i++) {
                JsonNode record = records.get(i);

                String id = record.get("qid").asText();
                String name = record.get("name_ja").asText();
                String desc = record.get("description_ja").asText();
                desc = desc.equals("null") ? desc : "";

                ujs.put(id, new UJ_DTO(id, name, desc));
            }

            req.setAttribute("jh_items", ujs);

            return ujs;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}