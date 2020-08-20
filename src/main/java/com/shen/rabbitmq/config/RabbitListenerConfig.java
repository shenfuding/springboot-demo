package com.shen.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitListenerConfig {
/*
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private DirectExchangeConfig directExchangeConfig;

    @Autowired
    private RabbitMsgAckListener rabbitMsgAckListener;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1); // 根据实际情况调整
        container.setMaxConcurrentConsumers(1); // 根据实际情况调整
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // Rabbit默认是自动确认，此处设置为手动确认
        container.setQueues(directExchangeConfig.directQueue()); // 指定需要手动ACK的队列
        // TODO add other queues
        // container.setQueues(...);
        container.setMessageListener(rabbitMsgAckListener);
        return container;
    }
*/
}