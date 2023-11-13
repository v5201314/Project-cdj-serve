package com.yusheng.util;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Mr.余
 * @date 2022/11/18
 * @package top.yxqz.resp
 * 验证码
 */
@WebServlet("/vcode/*")
public class VCodes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width = 200;
        int height = 35;
        /**
         * 实现步骤:
         * 	1.创建图像内存对象
         *  2.拿到画笔
         *  3.设置颜色，画矩形边框
         *  4.设置颜色，填充矩形
         *  5.设置颜色，画干扰线
         *  6.设置颜色，画验证码
         *  7.把内存图像输出到浏览器上
         */
        //创建内存图像
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//参数：宽度，高度 （指的都是像素），使用的格式（RGB）
        Graphics g = image.getGraphics();//画笔就一根

        //设置颜色
        g.setColor(Color.BLUE);
        //画边框
        g.drawRect(0, 0, width, height);

        //设置颜色
        g.setColor(Color.GRAY);
        //填充矩形
        g.fillRect(1, 1, width - 2, height - 2);

        //设置颜色
        g.setColor(Color.WHITE);
        //拿随机数对象
        Random r = new Random();
        //画干扰线 10条
        for (int i = 0; i < 10; i++) {
            g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
        }

        //设置颜色
        g.setColor(Color.RED);
        //改变字体大小
        Font font = new Font("宋体", Font.BOLD, 30);//参数：1字体名称。2.字体样式 3.字体大小
        g.setFont(font);//设置字体
        //画验证码	4个
        int x = 35;//第一个数的横坐标是35像素
        int codes[] = new int[4];

        for (int i = 0; i < 4; i++) {
            //r.nextInt(10)+""这种写法效率是十分低的
//            valueof ：将int类型装成String类型
            int code = r.nextInt(10); //随机的验证码
            codes[i] = code; //将验证码丢在数组里面
//            画图
            g.drawString(String.valueOf(code), x, 25);
            x += 35;
        }

        //输出到浏览器上
        //参数： 1.内存对象。2.输出的图片格式。3.使用的输出流
        ImageIO.write(image, "jpg", response.getOutputStream());
        System.out.println("当前的验证码是："+ Arrays.toString(codes));
        ServletContext context = getServletContext();
        context.setAttribute("vcodes",codes); //将验证码数据，丢到Application域中去
    }
}
