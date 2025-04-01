package com.uts.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uts.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT COUNT(*) FROM users WHERE email = #{email}")
    int countByEmail(@Param("email") String email);

    @Select("SELECT COUNT(*) FROM users WHERE username = #{username}")
    int countByUserName(@Param("username") String username);

    @Select("SELECT COUNT(*) FROM users WHERE username = #{username} AND password = #{password}")
    int findUser(@Param("username") String username, @Param("password") String password);

    @Select("SELECT COUNT(*) FROM users WHERE email = #{email} AND password = #{password}")
    int findUserByEmail(@Param("email") String email, @Param("password") String password);
}
