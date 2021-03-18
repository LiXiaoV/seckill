package com.xiaov.seckill.service;

import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.entity.OrderInfo;
import com.xiaov.seckill.vo.GoodsVo;

/**
 * @author xiaov
 * @since 2021-03-07 16:02
 */
public interface ImiaoshaService {
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods);

    public long getMiaoshaResult(Long userId, Long goodsId);
}
