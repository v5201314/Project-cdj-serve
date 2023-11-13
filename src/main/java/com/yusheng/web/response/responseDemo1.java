package com.yusheng.web.response;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/responseDemo1")
public class responseDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("resp1....");

        //重定向
//        //设置响应状态码
//        response.setStatus(302);
//        //设置响应头location
//        response.setHeader("location","/test11/responseDemo2");

        //简化方式
        response.sendRedirect("/test11/responseDemo2");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
