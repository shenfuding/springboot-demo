package com.shen.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交换机类型：Topic  按规则转发消息，最为灵活
 * 其实跟直连交换机流程差不多，但是它的特点就是在它的路由键和绑定键之间是有规则的
 * 路由键规则 #号和*号
 * 当#号和*号在末尾时效果等同，即
 * *  (星号) 匹配0个或者一个或者多个单词，只要*号前面的匹配上了就行了
 * #  (井号) 匹配0个或者一个或者多个单词，只要#号前面的匹配上了就行了
 * 当#号和*号在中间时才会有区别
 * *  (星号) 匹配0个或者一个单词
 * #  (井号) 匹配0个或者一个或者多个单词
 * 注意，交换机模式下路由键必须是.号隔开
 */
@Configuration
public class TopicExchangeConfig {

//    private static final String TOPIC_QUEUE_ORDER_CREATE = "TOPIC_QUEUE_ORDER_CREATE";
//    private static final String TOPIC_QUEUE_ORDER_SEND_GOODS = "TOPIC_QUEUE_ORDER_SEND_GOODS";
//
//    @Bean
//    public Queue createOrderTopicQueue() {
//        return new Queue(TOPIC_QUEUE_ORDER_CREATE, true); // durable指定true表示需要持久化队列，rabbitmq服务重启后保证队列还在
//    }
//
//    @Bean
//    public Queue sendGoodsTopicQueue() {
//        return new Queue(TOPIC_QUEUE_ORDER_SEND_GOODS, true); // durable指定true表示需要持久化队列，rabbitmq服务重启后保证队列还在
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {
//        String exchangeName = "orderTopicExchange";
//        return new TopicExchange(exchangeName, true, false); // durable指定true表示持久化交换机、autoDelete指定为false表示不自动删除
//    }
//
//    @Bean
//    public Binding bindingTopicQueueAndExchange() {
//        String routingKey = "TOPIC.ROUTING.KEY.CREATE";
//        return BindingBuilder.bind(createOrderTopicQueue()).to(topicExchange()).with(routingKey);
//    }
//
//    @Bean
//    public Binding bindingTopicQueueAndExchange2() {
//        String routingKey = "TOPIC.ROUTING.#.KEY";
//        return BindingBuilder.bind(sendGoodsTopicQueue()).to(topicExchange()).with(routingKey);
//    }

}
