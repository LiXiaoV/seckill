package com.xiaov.seckill.redis;

/**
 * @author xiaov
 * @since 2021-03-09 16:02
 */
public class GoodsKey extends BasePrefix{

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0,"gs");
}
