package com.xiaov.seckill.util;

import java.util.UUID;

/**
 * @author xiaov
 * @since 2021-03-05 10:07
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
