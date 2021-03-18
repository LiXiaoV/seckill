package com.xiaov.seckill.service.impl;

import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.exception.GlobalException;
import com.xiaov.seckill.mapper.MiaoshaUserMapper;
import com.xiaov.seckill.redis.MiaoshaUserKey;
import com.xiaov.seckill.redis.RedisService;
import com.xiaov.seckill.result.CodeMsg;
import com.xiaov.seckill.service.IMiaoshaUserService;
import com.xiaov.seckill.util.MD5Util;
import com.xiaov.seckill.util.UUIDUtil;
import com.xiaov.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaov
 * @since 2021-03-04 10:48
 */
@Service
public class MiaoshaUserServiceImpl implements IMiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    private MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public MiaoshaUser getById(Long id) {
        //取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if(user != null) return user;

        //取数据库
        user = miaoshaUserMapper.getById(id);
        if(user != null) redisService.set(MiaoshaUserKey.getById,""+id,user);
        return user;
    }

    @Override
    public boolean updatePassword(String token, long id, String formPass) {
        // 取user
        MiaoshaUser user = getById(id);
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass,user.getSalt()));
        miaoshaUserMapper.update(toBeUpdate);

        //更新数据库成功，修改缓存
        redisService.delete(MiaoshaUserKey.getById , ""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token, ""+token,user);
        return true;
    }

    @Override
    public boolean register(HttpServletResponse response, String username, String password, String salt) {
        return false;
    }

    @Override
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        // 验证用户是否注册
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        // 验证表单密码加密之后是否与数据库密码吻合
        String dbPass = user.getPassword();
        String saltDb = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(password,saltDb);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie 将session返回游览器 分布式session
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);
        return true;
    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        if(user != null){
            addCookie(response,token,user);
        }
        return user;
    }

    @Override
    public String createToken(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo ==null){
            throw  new GlobalException(CodeMsg.SERVER_ERROR);
        }

        String mobile =loginVo.getMobile();
        String password =loginVo.getPassword();
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        String dbPass = user.getPassword();
        String saltDb = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(password,saltDb);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie 将session返回游览器 分布式session
        String token= UUIDUtil.uuid();
        addCookie(response, token, user);
        return token ;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);

        //设置有效期
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
