package com.example.git_priv;

import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/region")
public class RegionServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        SqlSession ss = MyBatis.connect();

        List<TravelInfo_DTO> dto = ss.selectList("query.getRegion");

        req.setAttribute("region", dto);

        req.getRequestDispatcher("db_region.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        SqlSession ss = MyBatis.connect();

        List<TravelInfo_DTO> region = ss.selectList("query.getRegion");

        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        Map<String, String> params = new HashMap<>();
        params.put("region", req.getParameter("sel_region"));
        List<TravelInfo_DTO> ret = ss.selectList("query.getRegionInfo", params);

        req.setAttribute("region", region);
        System.out.println( req.getParameter("sel_region") );
        req.setAttribute("selected_region", req.getParameter("sel_region"));
        req.setAttribute("ret", ret);

        req.getRequestDispatcher("db_region.jsp").forward(req, res);
    }
}