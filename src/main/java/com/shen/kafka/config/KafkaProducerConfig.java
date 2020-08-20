package com.shen.kafka.config;

import com.shen.kafka.properties.KafkaProducerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    @Bean
    public Map<String, Object> producerConfigs() {
        log.info("-->Kafka producer config properties: {}", kafkaProducerProperties);
        Map<String, Object> props = new HashMap<String, Object>(8);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerProperties.getBootstrapServers());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProducerProperties.getBufferMemory());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerProperties.getBatchSize());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerProperties.getLingerMs());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerProperties.getAcks());
//        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerProperties.getRetries());
//        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, kafkaProducerProperties.getRetriesBackoffMs());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(producerFactory());
    }

}
