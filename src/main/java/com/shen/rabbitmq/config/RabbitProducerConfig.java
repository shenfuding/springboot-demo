package com.shen.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitProducerConfig {
//    @Bean
//    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.setConnectionFactory(connectionFactory);
//        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
//        rabbitTemplate.setMandatory(true);
//
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                log.info("-->交换机确认结果，ack:{}, cause:{}, correlationData:{}", ack, cause, correlationData);
//                if(!ack) {
//                    log.error("-->消息未发送成功");
//                    // TODO 扩展处理
//                }
//            }
//        });
//
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                log.info("-->队列确认结果，message:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}",
//                        message, replyCode, replyText, exchange, routingKey);
//            }
//        });
//
//        return rabbitTemplate;
//    }

}
