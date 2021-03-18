package com.xiaov.seckill.vo;

import com.xiaov.seckill.entity.MiaoshaUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author xiaov
 * @since 2021-03-09 17:25
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVo {
    private int miaoshaStatus = 0;
    private int remainSeconds = -1;
    private GoodsVo goods;
    private MiaoshaUser user;
}
