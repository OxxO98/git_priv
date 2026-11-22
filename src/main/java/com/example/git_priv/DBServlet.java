package com.example.git_priv;

import com.example.git_priv.DTO.*;
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

@WebServlet("/result")
public class DBServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        SqlSession ss = MyBatis.connect();

        String no = req.getParameter("no");

        Map<String, String> params = new HashMap<>();
        params.put("id", no);
        List<TravelInfo_DTO> dto = ss.selectList("query.getTravelInfo", params);

        req.setAttribute("ret", dto.get(0));

        req.getRequestDispatcher("db_tls.jsp").forward(req, res);
    }
}