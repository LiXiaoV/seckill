package com.xiaov.seckill.mapper;

import com.xiaov.seckill.entity.Goods;
import com.xiaov.seckill.entity.MiaoshaGoods;
import com.xiaov.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xiaov
 * @since 2021-03-06 16:17
 */
@Mapper
public interface GoodsMapper {
    public List<GoodsVo> getMiaoshaGoodsList();

    public GoodsVo getMiaoshaGoodsById(Long id);

    public int reduceStock(MiaoshaGoods goods);
}
