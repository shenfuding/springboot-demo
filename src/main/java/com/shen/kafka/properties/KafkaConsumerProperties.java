package com.shen.kafka.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
public class KafkaConsumerProperties {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.max-poll-records}")
    private String maxPollRecords;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private String enableAutoCommit;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetRest;

    @Value("${spring.kafka.consumer.session-timeout-ms}")
    private String sessionTimeoutMs;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

}
