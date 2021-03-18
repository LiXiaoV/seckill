package com.xiaov.seckill.service;

import com.xiaov.seckill.vo.GoodsVo;

import java.util.List;

/**
 * @author xiaov
 * @since 2021-03-06 16:16
 */
public interface IGoodsService {
    public List<GoodsVo> getMiaoshaGoodsList();

    public GoodsVo getMiaoshaGoodsById(Long id);

    public boolean reduceStock(GoodsVo goodsVo);
}
