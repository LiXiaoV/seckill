package com.xiaov.seckill.redis;

/**
 * @author xiaov
 * @since 2021-03-03 11:59
 */
public class OrderKey extends BasePrefix{
    private OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getMiaoshaOrderByUserIdGoodsId = new OrderKey("moug");
}
