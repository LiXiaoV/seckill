package com.xiaov.seckill.controller;

import com.xiaov.seckill.entity.MiaoshaOrder;
import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.exception.GlobalException;
import com.xiaov.seckill.rabbitmq.MQSender;
import com.xiaov.seckill.rabbitmq.MiaoshaMessage;
import com.xiaov.seckill.redis.GoodsKey;
import com.xiaov.seckill.redis.RedisService;
import com.xiaov.seckill.result.CodeMsg;
import com.xiaov.seckill.result.Result;
import com.xiaov.seckill.service.*;
import com.xiaov.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaov
 * @since 2021-03-07 15:37
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private ImiaoshaService miaoshaService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;

    private Map<Long,Boolean> localOverMap = new HashMap<>();
    /**
     * 系统初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.getMiaoshaGoodsList();
        if(goodsList == null){
            return;
        }
        for (GoodsVo goodsVo : goodsList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock,
                    ""+ goodsVo.getId(),goodsVo.getStockCount());
            localOverMap.put(goodsVo.getId(),false);
        }
    }

    /**
     * QPS: 3344 库存为0
     * QPS：997 库存20000,并且在持续增长中
     * QPS: 2815 库存8,并且在持续增长中
     * 3000 * 10
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> doMiaosha(MiaoshaUser user,
                            @RequestParam("goodsId") Long goodsId){
//        model.addAttribute("user",user);
        if(user == null){
//            return "login";
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }

        Boolean over = localOverMap.get(goodsId);
        if(over){
            throw new GlobalException(CodeMsg.MIAO_SHA_OVER);
        }
        // 预减库存
        Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if(stock <0){
            localOverMap.put(goodsId,true);
            throw new GlobalException(CodeMsg.MIAO_SHA_OVER);
        }

        // 判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null){
            throw new GlobalException(CodeMsg.REPEAT_MIAOSHA);
//            model.addAttribute("errorMsg", CodeMsg.REPEAT_MIAOSHA.getMsg());
//            return "miaosha_fail";
        }

        // 入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(mm);
        return Result.success(0);

//        // 判断库存
//        GoodsVo goods = goodsService.getMiaoshaGoodsById(goodsId);
//        int stock = goods.getStockCount();
//        if(stock <= 0){
//            throw new GlobalException(CodeMsg.MIAO_SHA_OVER);
////            model.addAttribute("errorMsg", CodeMsg.MIAO_SHA_OVER.getMsg());
////            return "miaosha_fail";
//        }
//
//
//        // 减库存、下订单、写入秒杀订单
//        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
//        OrderDetailVo orderDetailVo = new OrderDetailVo();
//        orderDetailVo.setOrder(orderInfo);
//        orderDetailVo.setGoods(goods);
////        model.addAttribute("orderInfo",orderInfo);
////        model.addAttribute("goods",goods);
////        return "order_detail";
//        return Result.success(orderDetailVo);
    }

    @RequestMapping("/path")
    @ResponseBody
    public Result<Boolean> path(){
        return Result.success(true);
    }

    /**
     * @param user
     * @param goodsId
     * @return
     * 秒杀成功 ：orderId
     * 秒杀失败： -1
     * 排队中：0
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(MiaoshaUser user,
                                     @RequestParam("goodsId") Long goodsId) {
//        model.addAttribute("user",user);
        if (user == null) {
//            return "login";
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

}
