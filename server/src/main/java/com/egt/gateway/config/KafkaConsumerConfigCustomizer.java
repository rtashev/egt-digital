package com.egt.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerConfigCustomizer {

    public KafkaConsumerConfigCustomizer(
            @Value("${kafka.consumers.idle-between-polls-ms}") long idleBetweenPollsMs,
            ConcurrentKafkaListenerContainerFactory<?, ?> factory
    ) {
        factory.setBatchListener(false);
        factory.getContainerProperties().setIdleBetweenPolls(idleBetweenPollsMs);
    }
}
