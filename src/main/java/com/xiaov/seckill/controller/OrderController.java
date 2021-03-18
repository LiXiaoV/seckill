package com.xiaov.seckill.controller;

import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.entity.OrderInfo;
import com.xiaov.seckill.result.CodeMsg;
import com.xiaov.seckill.result.Result;
import com.xiaov.seckill.service.IGoodsService;
import com.xiaov.seckill.service.IOrderService;
import com.xiaov.seckill.vo.GoodsVo;
import com.xiaov.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiaov
 * @since 2021-03-10 15:34
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(MiaoshaUser user,
                                      @RequestParam("orderId") long orderId){
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null)
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        Long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getMiaoshaGoodsById(goodsId);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(order);
        orderDetailVo.setGoods(goods);
        return Result.success(orderDetailVo);
    }
}
