package com.xiaov.seckill.mapper;

import com.xiaov.seckill.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author xiaov
 * @since 2021-03-02 10:51
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where id=#{id}")
    public User getById(@Param("id") int id);

    public User getByName(String name);

    @Insert("insert into user(id,name) values (#{id},#{name})")
    public int insert(User user);
}
