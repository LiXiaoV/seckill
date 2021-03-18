package com.xiaov.seckill.vo;

import com.xiaov.seckill.entity.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author xiaov
 * @since 2021-03-10 15:28
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;
}
