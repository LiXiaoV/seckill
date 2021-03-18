package com.xiaov.seckill.redis;

/**
 * @author xiaov
 * @since 2021-03-05 09:53
 */
public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(String prefix){
        super(prefix);
    }
    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");
}
