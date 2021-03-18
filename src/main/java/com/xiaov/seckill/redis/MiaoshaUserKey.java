package com.xiaov.seckill.redis;

/**
 * @author xiaov
 * @since 2021-03-05 09:53
 */
public class MiaoshaUserKey extends BasePrefix{
    public static final int TOKEN_EXPIRE = 3600 * 24 *2;
    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
//    public static MiaoshaUserKey getByNickName = new MiaoshaUserKey(0,"nickName");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");

    public MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
