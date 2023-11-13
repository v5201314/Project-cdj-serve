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
import com.google.gson.Gson;


@WebServlet("/Login")
public class LoginDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        ServletContext context = getServletContext();
        PrintWriter writer = resp.getWriter();
        //解决post乱码问题
        //req.setCharacterEncoding("UTF-8");

        //解决get和post乱码问题
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (yangZhen(req, context, writer)) return;

        //1.先对乱码数据进行编码
        byte[] bytes1 = username.getBytes(StandardCharsets.ISO_8859_1);
        byte[] bytes2 = password.getBytes(StandardCharsets.ISO_8859_1);
        //2.字节数组解码
        username = new String(bytes1, StandardCharsets.UTF_8);
        password = new String(bytes2, StandardCharsets.UTF_8);
        System.out.println(username);
        System.out.println(password);

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
        User user = userMapper.select(username, password);
        user.setPassword(null);

        //5.释放资源
        sqlSession.close();

        if(user!=null){
            //设置成功的状态码
            resp.setStatus(HttpServletResponse.SC_OK);
            // 设置成功消息
            Gson gson = new Gson();
            String jsonString = gson.toJson(user);
            writer.write(jsonString);
        }else {
            writer.println("登录失败");
        }
        writer.close();
    }

    public static boolean yangZhen(HttpServletRequest req, ServletContext context, PrintWriter writer) {
        String vcode=req.getParameter("vcode");//用户输入的验证码
        int[] vcodes= (int[]) context.getAttribute("vcodes");
        StringBuffer vcodes_data=new StringBuffer();
        for (int code:vcodes
        ) {
            vcodes_data.append(code);

        }
        if (!String.valueOf(vcodes_data).equals(vcode)) {
            writer.print("验证码错误");
            return true;
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
