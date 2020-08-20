package com.shen.rabbitmq.config;

import com.oracle.tools.packager.Log;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RabbitMsgAckListener {

}
/*
public class RabbitMsgAckListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag(); // 消息的ID
        try {
            String msg = message.toString(); // 如果要获取具体的消息内容，则要再从''中堆取
            log.info("监听到message:{}", message);
            // TODO 处理消息
            channel.basicAck(deliveryTag, true); // 手动ACK
//			channel.basicReject(deliveryTag, true); // reject表示拒绝消费当前消息，为true会重新放回队列，false则会被丢弃
         } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();

         }

    }
}
 */
