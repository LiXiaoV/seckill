package com.xiaov.seckill.result;

import lombok.Data;

/**
 * @author xiaov
 * @since 2021-03-01 21:50
 */
@Data
public class CodeMsg {
    private int code;
    private String msg;

    // 通用错误码
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101,"参数校验异常：%s");

    // 登陆模块 5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210,"Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211,"登陆密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212,"手机号码不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213,"手机号码格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214,"手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215,"密码错误");

    // 商品模块

    // 订单模块
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400,"订单不存在");

    // 秒杀模块
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500,"商品已经秒杀完毕");
    public static CodeMsg REPEAT_MIAOSHA = new CodeMsg(500501,"不能重复秒杀");


    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code,message);
    }
}
