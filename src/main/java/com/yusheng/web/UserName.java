package com.yusheng.web;

import com.yusheng.mapper.UserMapper;
import com.yusheng.pojo.User;
import com.yusheng.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/UserName")
public class UserName extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        ServletContext context = getServletContext();
        PrintWriter writer = resp.getWriter();

        String username=req.getParameter("username");

        //进行解码，防止中文乱码
        username=new String(username.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);

        //1. 获取 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory= SqlSessionFactoryUtils.getSqlSessionFactory();
        //2.获取 SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //3. 获取Mapper接口的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //4. 执行方法
        User u = userMapper.selectByUsername(username);

        if(u!=null){
            // 用户不存在，设置响应的状态码为404（未找到）
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            // 设置错误消息
            writer.write("用户名重复");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
