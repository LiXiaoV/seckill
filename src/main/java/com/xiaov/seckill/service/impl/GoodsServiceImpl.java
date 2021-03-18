package com.xiaov.seckill.service.impl;

import com.xiaov.seckill.entity.Goods;
import com.xiaov.seckill.entity.MiaoshaGoods;
import com.xiaov.seckill.mapper.GoodsMapper;
import com.xiaov.seckill.service.IGoodsService;
import com.xiaov.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaov
 * @since 2021-03-06 16:16
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public List<GoodsVo> getMiaoshaGoodsList() {
        return goodsMapper.getMiaoshaGoodsList();
    }

    @Override
    public GoodsVo getMiaoshaGoodsById(Long id) {
        return goodsMapper.getMiaoshaGoodsById(id);
    }

    @Override
    public boolean reduceStock(GoodsVo goodsVo) {
        MiaoshaGoods goods = new MiaoshaGoods();
        goods.setGoodsId(goodsVo.getId());
        goods.setStockCount(goodsVo.getStockCount() -1);
        int ret = goodsMapper.reduceStock(goods);
        return ret > 0;
    }
}
