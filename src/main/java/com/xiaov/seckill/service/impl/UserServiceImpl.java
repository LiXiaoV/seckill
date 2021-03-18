package com.xiaov.seckill.service.impl;

import com.xiaov.seckill.entity.User;
import com.xiaov.seckill.mapper.UserMapper;
import com.xiaov.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiaov
 * @since 2021-03-02 10:58
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(int id) {
        return userMapper.getById(id);
    }

    @Override
    public User getByName(String name) {
        return userMapper.getByName(name);
    }

    @Transactional
    @Override
    public boolean tx(){
        User u1 = new User();
        u1.setId(3);
        u1.setName("33333");
        userMapper.insert(u1);

        User u2 = new User();
        u1.setId(1);
        u1.setName("1111111");
        userMapper.insert(u2);
        return true;
    }
}
