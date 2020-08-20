package com.shen.rabbitmq.controller;

import com.rabbitmq.client.Channel;
import com.shen.httpresult.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class RabbitMQTestController {

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

//    @PostMapping("/rabbit/direct")
//    public HttpResult directExchnageTest() throws InterruptedException {
//        for(int i=0;i<1;i++) {
//            String msg = "hello rabbit " + i;
//            log.info("-->发送Direct消息: {}", msg);
//            rabbitTemplate.convertAndSend("directExchange", "directRoutingKey", msg);
//        }
//        return HttpResult.success();
//    }

//    @RabbitListener(queues = "directQueue")
//    public void listenDirectMessage1(String msg, Channel channel, @Headers Map<String, Object> headers) throws IOException {
//        log.info("-->监听到Direct消息: {}", msg);
//        log.info("-->headers:{}", headers);
//        long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
//        channel.basicAck(deliveryTag, true);
//    }


//    @PostMapping("/rabbit/topic")
//    public HttpResult topicExchnageTest() throws InterruptedException {
//        for(int i=0;i<1;i++) {
//            String msg = "hello rabbit " + i;
//            log.info("-->发送topic消息: {}", msg);
//            rabbitTemplate.convertAndSend("orderTopicExchange", "TOPIC.ROUTING.a.b.KEY", msg);
//        }
//        return HttpResult.success();
//    }
//
//    @RabbitListener(queues = "TOPIC_QUEUE_ORDER_CREATE")
//    public void listenDirectMessage2(String msg) {
//        log.info("-->监听到Topic(列队TOPIC_QUEUE_ORDER_CREATE)消息: {}", msg);
//    }
//    @RabbitListener(queues = "TOPIC_QUEUE_ORDER_SEND_GOODS")
//    public void listenDirectMessage3(String msg) {
//        log.info("-->监听到Topic(TOPIC_QUEUE_ORDER_SEND_GOODS)消息: {}", msg);
//    }
//
//    @PostMapping("/rabbit/fanout")
//    public HttpResult fanoutExchnageTest() throws InterruptedException {
//        for(int i=0;i<1;i++) {
//            String msg = "hello rabbit " + i;
//            log.info("-->发送fanout消息: {}", msg);
//            rabbitTemplate.convertAndSend("fanoutExchange", null, msg); // routing key为null
//        }
//        return HttpResult.success();
//    }
//
//    @RabbitListener(queues = "fanout.queue.a")
//    public void listenFanout1(String msg) {
//        log.info("-->监听到Fanout(队列fanout.queue.a)消息: {}", msg);
//    }
//    @RabbitListener(queues = "fanout.queue.b")
//    public void listenFanout2(String msg) {
//        log.info("-->监听到Fanout(队列fanout.queue.b)消息: {}", msg);
//    }
//    @RabbitListener(queues = "fanout.queue.c")
//    public void listenFanout3(String msg) {
//        log.info("-->监听到Fanout(队列fanout.queue.c)消息: {}", msg);
//    }
}
