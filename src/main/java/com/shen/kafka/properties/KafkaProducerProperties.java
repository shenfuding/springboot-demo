package com.shen.kafka.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
public class KafkaProducerProperties {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.buffer-memory}")
    private String bufferMemory;

    @Value("${spring.kafka.producer.batch-size}")
    private String batchSize;

    @Value("${spring.kafka.producer.linger-ms}")
    private String lingerMs;

    @Value("${spring.kafka.producer.retries}")
    private String retries;

    @Value("${spring.kafka.producer.retries-backoff-ms}")
    private String retriesBackoffMs;

    @Value("${spring.kafka.producer.acks}")
    private String acks;

}
