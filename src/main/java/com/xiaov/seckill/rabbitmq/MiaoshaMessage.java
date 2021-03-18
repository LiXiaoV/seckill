package com.xiaov.seckill.rabbitmq;

import com.xiaov.seckill.entity.MiaoshaUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author xiaov
 * @since 2021-03-11 17:11
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;
}
