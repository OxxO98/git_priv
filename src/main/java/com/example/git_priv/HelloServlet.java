package com.example.git_priv;

import com.example.git_priv.DTO.*;
import org.apache.ibatis.session.SqlSession;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Map<String, tl_DTO> parsedTL =  JsonlParser.parseTL(req,"/json/r3_translations.jsonl");

        Map<String, BI_DTO> parsedBI = JsonlParser.parseBI(req, "/json/bunka_intangible.json");
        Map<String, MG_DTO> parsedMG = JsonlParser.parseMG(req, "/json/maff_gi.json");
        Map<String, JH_DTO> parsedJH = JsonlParser.parseJH(req, "/json/japan_heritage.json");
        Map<String, UJ_DTO> parsedUJ = JsonlParser.parseUJ(req, "/json/unesco_japan.json");

        // join all
        ParsedDAO.getInstance().joinWithBI( parsedTL, parsedBI);
        ParsedDAO.getInstance().joinWithMG( parsedTL, parsedMG);
        ParsedDAO.getInstance().joinWithJH( parsedTL, parsedJH);
        ParsedDAO.getInstance().joinWithUJ( parsedTL, parsedUJ);
        ParsedDAO.getInstance().joinRest( parsedTL);


        //어디가 출처인지 모름 wikipedia
        Map<String, tl_desc_cmp_DTO> parseTL_cmp = JsonlParser.parseTL_CMP(req, "/json/descriptions_complete.jsonl");
        ParsedDAO.getInstance().reviseTLComplete(parseTL_cmp);

        Map<String, String> ret = ParsedDAO.getInstance().parsedData;
        Map<String, String> cmp_ret = ParsedDAO.getInstance().paresdTlCompleteData;

        req.setAttribute("ret",ret);
        req.setAttribute("ret_size",ret.size());
        req.setAttribute("cmp_ret", cmp_ret);
        req.setAttribute("cmp_ret_size", cmp_ret.size());

        req.getRequestDispatcher("result_tls.jsp").forward(req, res);

//        SqlSession ss = MyBatis.connect();
//
//        Map<String, String> params = new HashMap<>();
//        params.put("id", "1");
//        List<TravelInfo_DTO> dto = ss.selectList("query.getTravelInfo", params);

//        .println(dto.get(0).getInfo_region());
//        System.out.println(dto.get(0).getInfo_title());
//        System.out.println(dto.get(0).getInfo_content());
    }
}