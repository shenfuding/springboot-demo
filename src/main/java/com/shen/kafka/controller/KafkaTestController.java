package com.shen.kafka.controller;

import com.shen.httpresult.HttpResult;
import com.shen.kafka.KafkaSendFailureException;
import com.shen.kafka.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/kafka")
@RestController
public class KafkaTestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /*

    如果kafka服务停止了，那么发送消息失败，达到60秒才会抛出异常

     */

//    @PostMapping("/producer/sync")
//    public HttpResult testSyncProducer() {
//        log.info("测试同步发送消息开始");
//        long start = System.currentTimeMillis();
//        for(int i=0;i<1;i++) {
//            Order order = new Order();
//            order.setOrderNo("2013123123131331111");
//            log.info("-->发送同步消息:{}", order);
//            try {
//                SendResult result =  kafkaTemplate.send("order", order).get(); // 调用get方法即为同步发送
//                log.info("发送成功, topic-partition-offset:{}-{}-{}", result.getRecordMetadata().topic(), result.getRecordMetadata().partition() ,result.getRecordMetadata().offset());
//            } catch (Exception e) {
//                log.error("-->发送同步消息异常", e);
//            }
//        }
//        long end = System.currentTimeMillis();
//        log.info("-->发送同步消息完成!!!!!!!!!!耗时:{}", (end-start));
//        return HttpResult.success();
//    }

    @PostMapping("/producer/sync")
    public HttpResult testSyncProducer() {
        log.info("测试同步发送消息开始");
        long start = System.currentTimeMillis();
        for(int i=0;i<10;i++) {
            String msg = "hello world " + i;
            if(i%2==0) {
                msg = "kafka message";
            }
            log.info("-->发送同步消息:{}", msg);
            try {
                SendResult result =  kafkaTemplate.send("order2", msg).get(); // 调用get方法即为同步发送
                log.info("发送成功, topic-partition-offset:{}-{}-{}", result.getRecordMetadata().topic(), result.getRecordMetadata().partition() ,result.getRecordMetadata().offset());
            } catch (Exception e) {
                log.error("-->发送同步消息异常", e);
            }
        }
        long end = System.currentTimeMillis();
        log.info("-->发送同步消息完成!!!!!!!!!!耗时:{}", (end-start));
        return HttpResult.success();
    }

    @PostMapping("/producer/async")
    public HttpResult testAsyncProducer() {
        log.info("测试异步发送消息开始");
        long start = System.currentTimeMillis();
        for(int i=0;i<1;i++) {
            String msg = "hello world " + i;
            ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send("order", msg);
            log.info("-->发送异步消息:{}", msg);
            send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    log.info("发送成功, topic-partition-offset:{}-{}-{}", result.getRecordMetadata().topic(), result.getRecordMetadata().partition() ,result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable e) {
                    log.error("-->发送异步消息失败", e);
                }
            });
        }
        long end = System.currentTimeMillis();
        log.info("-->发送异步消息完成!!!!!!!!!!耗时:{}", (end-start));
        return HttpResult.success();
    }

    /**
     * 当设置factory.setBatchListener(false) 时用String来接收
     * 用String接收有可能一次接收到多条信息(如消费者挂掉一会儿，多条消息未被消费，然后消费者又启动了)，即msg的内容是多个消息内容拼接在一起的，这样程序处理会出问题，慎用
     * @param msg
     */
//    @KafkaListener(topics = "order2", groupId = "group-order")
//    public void onMessage(String msg, Acknowledgment ack) {
//        log.info("-->消费者一监听到消息: {}", msg);
//        ack.acknowledge();
//    }

    /**
     * 当设置factory.setBatchListener(true) 时用List<String>来接收
     * 用List接收一次接收到多条消息
     * @param msgs
     */
//    @KafkaListener(topics = "order", groupId = "group-order")
//    public void onMessage(List<String> msgs, Acknowledgment ack) {
//        log.info("-->监听到消息: {}条", msgs.size());
//        int i=0;
//        for(String msg : msgs) {
//            i++;
//            log.info("-->监听到消息: {}", msg);
//        }
//        // 只有将一次性监听到的消息处理完毕后，才进行ack，如果在处理时程序中断，则服务启动后，可保证重新开始消费消息，毕竟没有提交offset，所以处理方法可考虑放到事务中
//        ack.acknowledge();
//    }

}
