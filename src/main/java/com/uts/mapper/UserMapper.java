package com.uts.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uts.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT COUNT(*) FROM t_user WHERE email = #{email}")
    int countByEmail(@Param("email") String email);

    @Select("SELECT COUNT(*) FROM t_user WHERE username = #{username}")
    int countByUserName(@Param("username") String email);

    @Select("SELECT COUNT(*) FROM t_user WHERE username = #{username} and password=#{password}")
    int findUser(@Param("username") String username,@Param("password") String password);
}
