package com.xiaov.seckill.service;

import com.xiaov.seckill.entity.MiaoshaOrder;
import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.entity.OrderInfo;
import com.xiaov.seckill.vo.GoodsVo;

/**
 * @author xiaov
 * @since 2021-03-07 15:53
 */
public interface IOrderService {

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId,Long goodsId);

    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods);

    public OrderInfo getOrderById(long orderid);
}
