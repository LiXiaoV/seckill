package com.xiaov.seckill.service;

import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.entity.User;

/**
 * @author xiaov
 * @since 2021-03-02 10:57
 */
public interface IUserService {
    User getById(int id);
    User getByName(String name);
    boolean tx();
}
