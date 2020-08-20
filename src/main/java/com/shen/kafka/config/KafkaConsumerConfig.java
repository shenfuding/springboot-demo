package com.shen.kafka.config;

import com.shen.kafka.properties.KafkaConsumerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private KafkaConsumerProperties kafkaConsumerProperties;

    @Bean
    public Map<String, Object> consumerConfigs() {
        log.info("-->Kafka consumer config properties: {}", kafkaConsumerProperties);
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerProperties.getBootstrapServers());
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaConsumerProperties.getMaxPollRecords());
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConsumerProperties.getEnableAutoCommit());
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaConsumerProperties.getSessionTimeoutMs());
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG,kafkaConsumerProperties.getGroupId());
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerProperties.getAutoOffsetRest());
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return propsMap;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
    
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.setBatchListener(true); // true时用List<String>接收消息，一次最多取的条数为max-poll-records，false时用String接收消息
        factory.getContainerProperties().setPollTimeout(3000);
        // ackMode:
        //  RECORD 每处理一条commit一次
        //  BATCH(默认)  每次poll的时候批量提交一次，频率取决于每次poll的调用频率
        //  TIME  每次间隔ackTime的时间去commit
        //  COUNT  累积达到ackCount次的ack去commit
        //  COUNT_TIME ackTime或ackCount哪个条件先满足，就commit
        //  MANUAL listener负责ack，但是背后也是批量commit
        //  MANUAL_IMMEDIATE listner负责ack，每调用一次，就立即commit
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

}