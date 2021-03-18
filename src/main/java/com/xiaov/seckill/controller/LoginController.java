package com.xiaov.seckill.controller;

import com.xiaov.seckill.result.CodeMsg;
import com.xiaov.seckill.result.Result;
import com.xiaov.seckill.service.IMiaoshaUserService;
import com.xiaov.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author xiaov
 * @since 2021-03-04 09:52
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IMiaoshaUserService userService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        logger.info(loginVo.toString());
        userService.login(response, loginVo);
        return Result.success(true);
    }

    @RequestMapping("/create_token")
    @ResponseBody
    public String createToken(HttpServletResponse response, @Valid LoginVo loginVo){
        logger.info(loginVo.toString());
        String token = userService.createToken(response, loginVo);
        return token;
    }


}
