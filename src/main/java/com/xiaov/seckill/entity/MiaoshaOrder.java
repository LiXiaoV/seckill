package com.xiaov.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xiaov
 * @since 2021-03-06 16:08
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaOrder implements Serializable {

    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;
}
