package com.egt.gateway.config;

import com.egt.gateway.config.domain.KafkaTopicConfigProperties;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaProducerConfig {


    @Bean
    @ConfigurationProperties("kafka.producers.topics.session-events-topic")
    public KafkaTopicConfigProperties sessionEventsConfig()
    {
        return new KafkaTopicConfigProperties();
    }

    @Bean
    public NewTopic matchedOrdersTopicConfig(KafkaTopicConfigProperties sessionEventsConfig)
    {
        return buildNewTopicFromConfig(sessionEventsConfig);
    }

    private NewTopic buildNewTopicFromConfig(KafkaTopicConfigProperties kafkaTopicConfigProperties)
    {
        int replicas = kafkaTopicConfigProperties.getReplicas();

        return TopicBuilder.name(kafkaTopicConfigProperties.getName())
                .partitions(kafkaTopicConfigProperties.getPartitions())
                .replicas(replicas)
                .config(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, String.valueOf(replicas))
                .config(TopicConfig.RETENTION_MS_CONFIG, kafkaTopicConfigProperties.getRetentionMs())
                .build();
    }
}
