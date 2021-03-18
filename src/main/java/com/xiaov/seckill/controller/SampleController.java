package com.xiaov.seckill.controller;

import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.entity.User;
import com.xiaov.seckill.rabbitmq.MQSender;
import com.xiaov.seckill.redis.RedisService;
import com.xiaov.seckill.redis.UserKey;
import com.xiaov.seckill.result.CodeMsg;
import com.xiaov.seckill.result.Result;
import com.xiaov.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiaov
 * @since 2021-03-02 10:44
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","xiaov");
        return "hello";
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(2);
        return Result.success(user);
    }

    @RequestMapping("/db/getxml")
    @ResponseBody
    public Result<User> dbGetXml(){
        User user = userService.getByName("xiaov");
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user = redisService.get(UserKey.getById,""+1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("111111");
        boolean ret = redisService.set(UserKey.getById,""+1, user);
        return Result.success(ret);
    }

    @RequestMapping("/user/info")
    @ResponseBody
    public Result<MiaoshaUser> userInfo(MiaoshaUser user){
        return Result.success(user);
    }

//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result<String> mq(){
//        sender.send("hello rabbitmq!");
//        return Result.success("hello rabbitmq!");
//    }
//
//    @RequestMapping("/mq/topic")
//    @ResponseBody
//    public Result<String> topic(){
//        sender.sendTopicMessage("hello rabbitmq!");
//        return Result.success("hello rabbitmq!");
//    }
//
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public Result<String> fanout(){
//        sender.sendFanoutMessage("hello rabbitmq!");
//        return Result.success("hello rabbitmq!");
//    }
//
//    @RequestMapping("/mq/headers")
//    @ResponseBody
//    public Result<String> headersSent(){
//        sender.sendHeadersMessage("hello rabbitmq!");
//        return Result.success("hello rabbitmq!");
//    }
}
