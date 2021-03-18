package com.xiaov.seckill.service.impl;

import com.xiaov.seckill.entity.Goods;
import com.xiaov.seckill.entity.MiaoshaOrder;
import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.entity.OrderInfo;
import com.xiaov.seckill.mapper.GoodsMapper;
import com.xiaov.seckill.redis.MiaoshaKey;
import com.xiaov.seckill.redis.RedisService;
import com.xiaov.seckill.service.IGoodsService;
import com.xiaov.seckill.service.IOrderService;
import com.xiaov.seckill.service.ImiaoshaService;
import com.xiaov.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiaov
 * @since 2021-03-07 16:02
 */
@Service
public class MiaoshaServiceImpl implements ImiaoshaService {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        // 减库存、下订单、写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if(success){
            return orderService.createOrder(user,goods);
        }else {
            setGoodsOver(goods.getId());
            return null;
        }

    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver,""+goodsId,true);
    }

    @Override
    public long getMiaoshaResult(Long userId, Long goodsId) {
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if(miaoshaOrder != null){
            return miaoshaOrder.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver){
                return -1;
            }else
                return 0;
        }

    }

    private boolean getGoodsOver(Long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver,""+goodsId);
    }
}
