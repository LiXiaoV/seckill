package com.xiaov.seckill.service;

import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaov
 * @since 2021-03-04 10:41
 */
public interface IMiaoshaUserService {

    public MiaoshaUser getById(Long id);

    public boolean updatePassword(String token, long id, String formPass);

    public boolean register(HttpServletResponse response, String username, String password, String salt);

    public boolean login(HttpServletResponse response, LoginVo loginVo);

    public MiaoshaUser getByToken(HttpServletResponse response, String token);

    String createToken(HttpServletResponse response, LoginVo loginVo);
}
