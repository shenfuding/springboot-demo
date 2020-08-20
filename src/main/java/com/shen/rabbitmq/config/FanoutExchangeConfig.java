package com.shen.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交换机类型: Fanout
 * 这个交换机没有路由键概念，就算你绑了路由键也是无视的。 这个交换机在接收到消息后，会直接转发到绑定到它上面的所有队列
 */
@Configuration
public class FanoutExchangeConfig {

//    @Bean
//    public Queue queueA() {
//        return new Queue("fanout.queue.a", true); // durable指定true表示需要持久化队列，rabbitmq服务重启后保证队列还在
//    }
//    @Bean
//    public Queue queueB() {
//        return new Queue("fanout.queue.b", true); // durable指定true表示需要持久化队列，rabbitmq服务重启后保证队列还在
//    }
//    @Bean
//    public Queue queueC() {
//        return new Queue("fanout.queue.c", true); // durable指定true表示需要持久化队列，rabbitmq服务重启后保证队列还在
//    }
//
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        String exchangeName = "fanoutExchange";
//        return new FanoutExchange(exchangeName, true, false); // durable指定true表示持久化交换机、autoDelete指定为false表示不自动删除
//    }
//
//    @Bean
//    public Binding bindingFanoutQueueAndExchange() {
//        return BindingBuilder.bind(queueA()).to(fanoutExchange());
//    }
//    @Bean
//    public Binding bindingFanoutQueueAndExchange2() {
//        return BindingBuilder.bind(queueB()).to(fanoutExchange());
//    }
//    @Bean
//    public Binding bindingFanoutQueueAndExchange3() {
//        return BindingBuilder.bind(queueC()).to(fanoutExchange());
//    }

}
