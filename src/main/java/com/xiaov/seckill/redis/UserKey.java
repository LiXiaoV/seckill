package com.xiaov.seckill.redis;

/**
 * @author xiaov
 * @since 2021-03-03 11:58
 */
public class UserKey extends BasePrefix{
    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
