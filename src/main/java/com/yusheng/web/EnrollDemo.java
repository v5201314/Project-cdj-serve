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
import java.util.UUID;

@WebServlet("/Enroll")
public class EnrollDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        ServletContext context = getServletContext();
        PrintWriter writer = resp.getWriter();

        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String searchClass=req.getParameter("searchClass");
        if (LoginDemo.yangZhen(req, context, writer)) return;
        System.out.println(searchClass);
        //进行解码，防止中文乱码
        username=new String(username.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
        password=new String(password.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);

        //封装用户对象
        User user = new User();
        user.setUserName(username);
        //使用uuid生成id
        UUID uid=UUID.randomUUID();
        user.setId(uid.toString());
        user.setPassword(password);
        if (searchClass.equals("1")) {
            user.setPermissions(1);
        }else if(searchClass.equals("2")){
            user.setPermissions(2);
        }else {
            writer.print("目前只能注册为普通用户和商家");
            return;
        }

        //1. 获取 SqlSessionFactory
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSessionFactory sqlSessionFactory= SqlSessionFactoryUtils.getSqlSessionFactory();
        //2.获取 SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //3. 获取Mapper接口的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //4. 执行方法
        User u = userMapper.selectByUsername(username);
        if(u==null){

            userMapper.add(user);
            //提交事务
            sqlSession.commit();
            writer.print("注册成功");
            // 请求转发到登录页面
//            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/Login");
//            requestDispatcher.forward(req,resp);
            //5.释放资源
            sqlSession.close();
        }else{
            writer.print("用户名重复");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
