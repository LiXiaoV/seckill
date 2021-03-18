package com.xiaov.seckill;

import com.xiaov.seckill.entity.OrderInfo;
import com.xiaov.seckill.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
public class SeckillApplicationTests {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void TestOrderInfoInsert(){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoodsId(4L);
        orderInfo.setGoodsPrice(0.01);
        long insertId = orderMapper.insert(orderInfo);
        System.out.println("insertId = " + insertId);
    }

    @Test
    public void testInsert(){

    }

}
