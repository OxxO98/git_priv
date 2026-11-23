package com.example.git_priv;

import com.example.git_priv.DTO.BI_DTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/translate")
public class TranslateServlet extends HttpServlet {

    private static final String API_URL =
            "https://api.openai.com/v1/responses";

    private static final String API_KEY =
            System.getenv("OPENAI_API_KEY");

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws IOException {

        req.setCharacterEncoding("UTF-8");
//        res.setContentType("application/json;charset=UTF-8");

        String no = req.getParameter("no");
        String year = req.getParameter("year");
        String region = req.getParameter("region");
        String content = req.getParameter("content");

        if (content == null || content.trim().isEmpty()) {
            req.setAttribute("msg", "error : 번역할 텍스트가 없습니다.");
            return;
        }

        try {
            SqlSession ss = MyBatis.connect();

            Map<String, String> isParams = new HashMap();
            List<TravelInfo_DTO> isList =  ss.selectList("query.getTranslate", isParams);
            if( isList.size() > 0 ) {
                req.setAttribute("msg", "error : 이미 번역이 존재합니다.");
                return;
            }


            String prompt =
                    "Translate the following Japanese text into natural Korean. "
                            + "Preserve the original meaning and do not add explanations.\n\n"
                            + content;
            // 구조화된 응답 받기 고민 필요

            JsonObject body = new JsonObject();
            body.addProperty("model", "gpt-5-mini");
            body.addProperty("input", prompt);

            URL url = new URL(API_URL);
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty(
                    "Authorization", "Bearer " + API_KEY
            );
            conn.setRequestProperty(
                    "Content-Type", "application/json"
            );
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(60000);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.toString().getBytes("UTF-8"));
            }

            int status = conn.getResponseCode();

            InputStream is = status >= 200 && status < 300
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            StringBuilder result = new StringBuilder();

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(result.toString());
            JsonNode outputs = json.get("output");
            String translatedText = null;
            for (JsonNode output : outputs) {
                String type = output.get("type").asText();
                if( type.equals("message") ){
                    translatedText = output.get("content").get(0).get("text").asText();
                    req.setAttribute("translatedText", translatedText);
                    System.out.println(translatedText);
                    break;
                }
            }

            // update db
            Map<String, String> params = new HashMap<>();
            params.put("no", no);
//            params.put("year_ko", );
//            params.put("region_ko", );
//            params.put("title_ko", );
//            params.put("content_ko", );
            ss.update("query.addInfoTranslate", params);

            conn.disconnect();

            req.getRequestDispatcher("result_tls.jsp").forward(req, res);

        } catch (Exception e) {
            req.setAttribute("msg", "error : 번역 API 호출에 실패했습니다.");
        }
    }
}

