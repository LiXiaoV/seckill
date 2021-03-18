package com.xiaov.seckill.redis;

/**
 * 接口 -> 抽象类 -> 实现类
 * 实现根据各个模块划分key
 * @author xiaov
 * @since 2021-03-03 11:51
 */
public interface KeyPrefix {

    public int expireSeconds();
    public String getPrefix();
}
