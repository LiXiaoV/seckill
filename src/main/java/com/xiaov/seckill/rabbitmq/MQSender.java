package com.xiaov.seckill.rabbitmq;

import com.xiaov.seckill.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaov
 * @since 2021-03-10 22:01
 */
@Service
public class MQSender {

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMiaoshaMessage(MiaoshaMessage mm){
        String msg = RedisService.beanToString(mm);
        logger.info("send message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,msg);
    }
//    public void send(Object message){
//
//        String msg = RedisService.beanToString(message);
//        logger.info("send message:"+msg);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);
//    }
//
//    public void sendTopicMessage(Object message){
//
//        String msg = RedisService.beanToString(message);
//        logger.info("send topic message:"+msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key1",msg+1);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key2",msg+2);
//    }
//
//    public void sendFanoutMessage(Object message){
//
//        String msg = RedisService.beanToString(message);
//        logger.info("send fanout message:"+msg);
//        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"topic.key1",msg+1);
//        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"topic.key2",msg+2);
//    }
//
//    public void sendHeadersMessage(Object message){
//
//        String msg = RedisService.beanToString(message);
//        logger.info("send headers message:"+msg);
//        MessageProperties properties = new MessageProperties();
//        properties.setHeader("header1","value1");
//        properties.setHeader("header2","value2");
//
//        Message obj = new Message(msg.getBytes(), properties);
//        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE,"",obj);
//
//    }
}
