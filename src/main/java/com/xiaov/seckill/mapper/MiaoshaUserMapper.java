package com.xiaov.seckill.mapper;

import com.xiaov.seckill.entity.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author xiaov
 * @since 2021-03-04 10:33
 */
@Mapper
public interface MiaoshaUserMapper {

    @Select("select * from miaosha_user where nickname = #{nickname}")
    public MiaoshaUser getByNickname(@Param("nickname") String nickname);

    @Select("select * from miaosha_user where id = #{id}")
    public MiaoshaUser getById(@Param("id") Long id);

    @Update("update miaosha_user set password = #{password} where id = #{id}")
    public void update(MiaoshaUser miaoshaUser);
}
