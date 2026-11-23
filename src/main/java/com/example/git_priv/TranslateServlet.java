package com.example.git_priv;

import com.example.git_priv.DTO.BI_DTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        res.setContentType("application/json;charset=UTF-8");
//
//        String no = req.getParameter("no");
//        String title = req.getParameter("title");
//        String content = req.getParameter("content");
        ObjectMapper paramMapper = new ObjectMapper();

        JsonNode requestJson = paramMapper.readTree(req.getInputStream());

        System.out.println(requestJson);
        String no = requestJson.get("no").asText();
        String title = requestJson.get("title").asText();
        String content = requestJson.get("content").asText();
        System.out.println(no + " " + title + " ");

        if (content == null || content.trim().isEmpty()) {
            req.setAttribute("msg", "error : 번역할 텍스트가 없습니다.");
            return;
        }

        try {
            SqlSession ss = MyBatis.connect();

            Map<String, String> isParams = new HashMap();
            isParams.put("no", no);

            List<TravelInfo_DTO> isList = ss.selectList("query.getTranslate", isParams);
            if (!isList.isEmpty()) {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");

                ObjectMapper mapper = new ObjectMapper();
                ObjectNode errorObj = mapper.createObjectNode();

                errorObj.put("success", false);
                errorObj.put("message", "이미 번역이 존재합니다.");

                res.getWriter().write(mapper.writeValueAsString(errorObj));
                return;
            }


            String prompt =
                    "Translate the following Japanese text into natural Korean. "
                            + "Preserve the original meaning and do not add explanations.\n\n"
                            + "Return the result in the following JSON format:\n"
                            + "{\"title\":\"translated title\", \"content\":\"translated content\"}\n\n"
                            + "title: " + title + "\n"
                            + "content: " + content;
            // 구조화된 응답 받기 고민 필요


            JsonObject body = new JsonObject();
            body.addProperty("model", "gpt-5-mini");
            body.addProperty("input", prompt);

            JsonObject text = new JsonObject();
            JsonObject format = new JsonObject();

            format.addProperty("type", "json_object");

            text.add("format", format);
            body.add("text", text);

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
            conn.setConnectTimeout(100000);
            conn.setReadTimeout(600000);

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

            // 응답 파싱
            ObjectMapper mapper = new ObjectMapper();

// 1. API 전체 응답 파싱
            JsonNode json = mapper.readTree(result.toString());

// 2. 응답 상태 확인
            String jsonStatus = json.path("status").asText();

            if (!"completed".equals(jsonStatus)) {
                throw new RuntimeException("API 응답 미완료: " + jsonStatus);
            }

// 3. output 배열 순회
            JsonNode outputs = json.path("output");

            String translatedText = null;

            for (JsonNode output : outputs) {

                if (!"message".equals(output.path("type").asText())) {
                    continue;
                }

                JsonNode contents = output.path("content");

                for (JsonNode contentNode : contents) {

                    String contentType = contentNode.path("type").asText();

                    if ("refusal".equals(contentType)) {
                        throw new RuntimeException("번역 거부: "
                                + contentNode.path("refusal").asText());
                    }

                    if ("output_text".equals(contentType)) {
                        translatedText = contentNode.path("text").asText();
                        break;
                    }
                }

                if (translatedText != null) {
                    break;
                }
            }

            if (translatedText == null) {
                throw new RuntimeException("번역 결과를 찾을 수 없습니다.");
            }

// 4. 번역 결과 JSON을 다시 파싱
            JsonNode translatedJson = mapper.readTree(translatedText);

            String translatedTitle = translatedJson.path("title").asText();
            String translatedContent = translatedJson.path("content").asText();

// 5. JSP로 전달
            req.setAttribute("translatedTitle", translatedTitle);
            req.setAttribute("translatedContent", translatedContent);

            System.out.println("제목: " + translatedTitle);
            System.out.println("내용: " + translatedContent);

            // update db
            Map<String, String> params = new HashMap<>();
            params.put("no", no);
            params.put("title_ko", translatedTitle);
            params.put("content_ko", translatedContent);
            if( ss.update("query.addInfoTranslate", params) == 1 ){
                System.out.println("update success");
                ss.commit();
            }

            ObjectMapper resMapper = new ObjectMapper();

            ObjectNode resultObj = resMapper.createObjectNode();
            resultObj.put("no", no);
            resultObj.put("title", translatedTitle);
            resultObj.put("content", translatedContent);

            res.getWriter().write(mapper.writeValueAsString(resultObj));

            conn.disconnect();

//            req.getRequestDispatcher("result_tls.jsp").forward(req, res);

        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode errorObj = mapper.createObjectNode();

            errorObj.put("success", false);
            errorObj.put("message", e.getMessage());

            res.getWriter().write(mapper.writeValueAsString(errorObj));
        }
    }
}

