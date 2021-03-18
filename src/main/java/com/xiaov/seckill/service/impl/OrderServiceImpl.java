package com.xiaov.seckill.service.impl;

import com.xiaov.seckill.entity.MiaoshaOrder;
import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.entity.OrderInfo;
import com.xiaov.seckill.mapper.OrderMapper;
import com.xiaov.seckill.redis.OrderKey;
import com.xiaov.seckill.redis.RedisService;
import com.xiaov.seckill.service.IOrderService;
import com.xiaov.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author xiaov
 * @since 2021-03-07 15:54
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId) {
        return redisService.get(OrderKey.getMiaoshaOrderByUserIdGoodsId,
                ""+userId+"_"+goodsId,MiaoshaOrder.class);
//        return orderMapper.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    @Override
    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setCreateDate(new Date());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderMapper.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        orderMapper.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.getMiaoshaOrderByUserIdGoodsId,
                ""+user.getId()+"_"+goods.getId(),miaoshaOrder);
        return orderInfo;
    }

    @Override
    public OrderInfo getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }
}
