package com.yusheng.mapper;

import com.yusheng.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    /**
     * 根据用户名和密码查询用户对象
     * @param userName
     * @param password
     * @return
     */
    @Select("select * from users where userName=#{userName} and password=#{password}")
    User select(@Param("userName") String userName,@Param("password") String password);


    /**
     * 根据用户名查询用户对象
     * @param userName
     * @return
     */
    @Select("select * from users where userName=#{userName}")
    User selectByUsername(String userName);


    @Insert("insert into users values(#{userName},#{id},#{avatar},#{password},#{permissions})")
    void add(User user);
}
