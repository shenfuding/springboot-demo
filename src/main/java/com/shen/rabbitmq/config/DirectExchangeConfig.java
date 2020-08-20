package com.shen.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交换机类型：Direct ，是默认的交换机模式
 * 其类型的行为是“先匹配、再投送”，即在绑定时设定一个 routing_key，消息的 routing_key 匹配时，才会被交换器投送到绑定的队列中去。
 */
@Configuration
public class DirectExchangeConfig {

//    /**
//     * 创建一个名为directQueue的队列
//     * @return
//     */
//    @Bean
//    public Queue directQueue() {
//        String queueName = "directQueue";
//        return new Queue(queueName, true); // durable指定true表示需要持久化队列，rabbitmq服务重启后保证队列还在
//    }
//
//    /**
//     * 创建一个名为directExchange的交换机
//     * @return
//     */
//    @Bean
//    public DirectExchange directExchange() {
//        String exchangeName = "directExchange";
//        return new DirectExchange(exchangeName, true, false); // durable指定true表示持久化交换机、autoDelete指定为false表示不自动删除
//    }
//
//    /**
//     * 将队列directQueue通过路由键directRoutingKey绑定到交换机directExchange上
//     * @return
//     */
//    @Bean
//    public Binding bindingDirectQueueAndExchange() {
//        String routingKey = "directRoutingKey";
//        return BindingBuilder.bind(directQueue()).to(directExchange()).with(routingKey);
//    }
}
