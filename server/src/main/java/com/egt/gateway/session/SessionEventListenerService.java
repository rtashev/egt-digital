package com.egt.gateway.session;

import com.egt.gateway.session.domain.event.SessionEvent;
import com.egt.gateway.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class SessionEventListenerService {

    @Autowired
    private SessionService sessionService;

    @KafkaListener(topics = "${kafka.consumers.topics.session-events-topic}")
    public void onSessionEvent(ConsumerRecord<String, String> record) throws InterruptedException {

        try
        {
            SessionEvent event = JsonUtils.fromJson(record.value(), SessionEvent.class);
            log.info("Processing request [{}] for session [{}]", event.getRequestId(), event.getSessionId());

            sessionService.onSessionEvent(event);
        }catch (Exception e)
        {
            log.error("Error processing session event", e);
            throw e;
        }
    }
}
