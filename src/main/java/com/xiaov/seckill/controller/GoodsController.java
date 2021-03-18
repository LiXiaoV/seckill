package com.xiaov.seckill.controller;

import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.redis.GoodsKey;
import com.xiaov.seckill.redis.RedisService;
import com.xiaov.seckill.result.Result;
import com.xiaov.seckill.service.IGoodsService;
import com.xiaov.seckill.service.IMiaoshaUserService;
import com.xiaov.seckill.service.IUserService;
import com.xiaov.seckill.service.impl.MiaoshaUserServiceImpl;
import com.xiaov.seckill.vo.GoodsDetailVo;
import com.xiaov.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;
import java.util.List;

/**
 * @author xiaov
 * @since 2021-03-05 21:19
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private IMiaoshaUserService userService;

    @Autowired
    private IGoodsService goodsService;

    @RequestMapping(value = "/to_list", produces = "text/html")
    public String toList(Model model,MiaoshaUser user) {
        model.addAttribute("user", user);

        // 查询商品列表
        List<GoodsVo> miaoshaGoodsList = goodsService.getMiaoshaGoodsList();
        model.addAttribute("goodsList", miaoshaGoodsList);

        return "goods_list";

//        // 取缓存
//        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
//        if(!StringUtils.isEmpty(html)) return html;


    }

    @RequestMapping("/to_detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(Model model, MiaoshaUser user,
                                        @PathVariable("goodsId")Long goodsId) {
//        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getMiaoshaGoodsById(goodsId);
//        model.addAttribute("goods", goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long nowTime = System.currentTimeMillis();
        int remainSeconds;
        int miaoshaStatus;

        if(nowTime < startTime){    //秒杀还没开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startTime - nowTime) / 1000);
        }else if (nowTime >endTime){    //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else { // 秒杀正在进行
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        goodsDetailVo.setUser(user);
        goodsDetailVo.setGoods(goods);
//        model.addAttribute("miaoshaStatus", miaoshaStatus);
//        model.addAttribute("remainSeconds", remainSeconds);
//        return "goods_detail";
        return Result.success(goodsDetailVo);
    }
}
