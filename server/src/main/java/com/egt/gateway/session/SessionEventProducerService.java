package com.egt.gateway.session;

import com.egt.gateway.session.domain.event.SessionEvent;
import com.egt.gateway.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionEventProducerService {

    private final String sessionEventTopic;
    private final KafkaTemplate kafkaTemplate;

    public SessionEventProducerService(@Value("${kafka.producers.topics.session-events-topic.name}") String sessionEventTopic,
                                       KafkaTemplate kafkaTemplate) {
        this.sessionEventTopic = sessionEventTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSessionEvent(SessionEvent sessionEvent) {
        String key = sessionEvent.getSessionId();
        String jsonData = JsonUtils.toJson(sessionEvent);
        ProducerRecord<String, String> record = new ProducerRecord<>(sessionEventTopic, key, jsonData);
        log.info("Sending request [{}] with session [{}] for async processing",
                sessionEvent.getRequestId(), sessionEvent.getSessionId());

        try {
            kafkaTemplate.send(record);
        } catch (Exception e)
        {
            log.error("Unable to send request [{}]", sessionEvent.getRequestId());
            throw e;
        }
    }

}
