package com.nowui.cloud.shop.product.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ZhongYongQiang
 */
@Component
public class ProductListener {

    @RabbitListener(queues = "queue.foo")
    public void receiveFooQueue(String message) {
        System.out.println("Received Foo<" + message + ">");
    }

    @RabbitListener(queues = "queue.bar")
    public void receiveBarQueue(String message) {
        System.out.println("Received Bar<" + message + ">");
    }

    @RabbitListener(queues = {"test"})
    public void receiveBarQueue2(String message) {
        System.out.println("Received Bar 2<" + message + ">");
    }

}
