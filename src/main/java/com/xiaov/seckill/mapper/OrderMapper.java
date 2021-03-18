package com.xiaov.seckill.mapper;

import com.xiaov.seckill.entity.MiaoshaOrder;
import com.xiaov.seckill.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @author xiaov
 * @since 2021-03-07 16:40
 */
@Mapper
public interface OrderMapper {

    public long insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    public long insert(OrderInfo orderInfo);

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId);

    public OrderInfo getOrderById(@Param("orderId") long orderId);
}
