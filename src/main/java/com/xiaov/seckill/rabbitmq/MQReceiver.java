package com.xiaov.seckill.rabbitmq;

import com.xiaov.seckill.entity.MiaoshaOrder;
import com.xiaov.seckill.entity.MiaoshaUser;
import com.xiaov.seckill.exception.GlobalException;
import com.xiaov.seckill.redis.RedisService;
import com.xiaov.seckill.result.CodeMsg;
import com.xiaov.seckill.service.IGoodsService;
import com.xiaov.seckill.service.IOrderService;
import com.xiaov.seckill.service.ImiaoshaService;
import com.xiaov.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaov
 * @since 2021-03-10 22:02
 */
@Service
public class MQReceiver {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ImiaoshaService miaoshaService;

    @Autowired
    private IOrderService orderService;

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    /**
     * Direct模式
     * @param message
     */
    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receive(String message){
        logger.info("receive message:" + message);
        MiaoshaMessage mm = RedisService.stringToBean(message, MiaoshaMessage.class);

        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        // 判断库存
        GoodsVo goods = goodsService.getMiaoshaGoodsById(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            throw new GlobalException(CodeMsg.MIAO_SHA_OVER);
        }

        // 判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goods.getId());
        if(order != null){
            throw new GlobalException(CodeMsg.REPEAT_MIAOSHA);
        }
        // 减库存、下订单、写入秒杀订单
        miaoshaService.miaosha(user,goods);
    }

//    /**
//     * Direct模式
//     * @param message
//     */
//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message){
//        logger.info("receive message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message){
//        logger.info("receive topic queue1 message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message){
//        logger.info("receive topic queue2 message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.FANOUT_QUEUE1)
//    public void receiveFanout1(String message){
//        logger.info("receive fanout queue1 message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.FANOUT_QUEUE2)
//    public void receiveFanout2(String message){
//        logger.info("receive fanout queue2 message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
//    public void receiveHeaders1(byte[] message){
//        logger.info("receive headers queue message:" + new String(message));
//    }
}
