package com.egt.gateway.session;

import com.egt.gateway.external_service.ExternalServiceClient;
import com.egt.gateway.external_service.domain.ExternalServiceResponse;
import com.egt.gateway.session.domain.SessionCacheObject;
import com.egt.gateway.session.domain.entity.SessionEntity;
import com.egt.gateway.session.domain.event.SessionEvent;
import com.egt.gateway.session.exception.RequestCurrentlyProcessingExeption;
import com.egt.gateway.session.exception.SessionAlreadyCompleteException;
import com.egt.gateway.session.exception.UnableToHandleSessionException;
import com.egt.gateway.util.JsonUtils;
import com.egt.gateway.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private final SessionEventProducerService sessionEventProducerService;

    private final ExternalServiceClient externalServiceClient;

    private final RedisUtil redisUtil;

    @Transactional
    public List<String> findRequestIds(String sessionId, String requestId)
    {
        tryAquireRequestLock(sessionId, requestId);

        var userSessionEntity = SessionEntity.builder()
                .sessionId(sessionId)
                .requestId(requestId)
                .build();

        insertSessionAndProduceEvent(userSessionEntity);

        return sessionRepository.getRequestIdsForCompletedSession(sessionId);

    }

    @Transactional
    public void insertSession(String sessionId, String requestId, String userId)
    {
        tryAquireRequestLock(sessionId, requestId);

        var userSessionEntity = SessionEntity.builder()
                .sessionId(sessionId)
                .requestId(requestId)
                .userId(userId)
                .build();

        insertSessionAndProduceEvent(userSessionEntity);
    }

    public void onSessionEvent(SessionEvent event) {

        ExternalServiceResponse response;
        try
        {
            response = externalServiceClient.getExternalServiceResponse();

        } catch (HttpClientErrorException e)
        {
            // TODO : handle reprocessing with retrycounter
            log.warn("Call to external service failed for request {}", event.getRequestId());
            return;
        }

        sessionRepository.updateSessionEntity(event.getId(), response.fact());
        redisUtil.remove(event.getRequestId());
        log.info("Finished request [{}] form session [{}]", event.getRequestId(), event.getRequestId());
    }

    @Transactional
    public void insertSessionAndProduceEvent(SessionEntity userSessionEntity) {
        try {

            int id = sessionRepository.insertSessionEntity(userSessionEntity);
            userSessionEntity.setId(id);
            if (userSessionEntity.getSessionId().equals("zxc"))
                throw new RuntimeException("");
            sessionEventProducerService.sendSessionEvent(SessionEvent.fromSessionEntity(userSessionEntity));

        } catch (Exception e) {
            redisUtil.remove(userSessionEntity.getRequestId());

            if (e instanceof DuplicateKeyException) {
                // session is already finished
                log.warn("Can't process request further for current session as it has already been processed.");
                throw new SessionAlreadyCompleteException(userSessionEntity.getSessionId(),
                        userSessionEntity.getRequestId());
            }

            log.error("Error occured while processing request {}", userSessionEntity.getRequestId(), e);
            throw new UnableToHandleSessionException(userSessionEntity.getSessionId(),
                    userSessionEntity.getRequestId());
        }
    }

    private void tryAquireRequestLock(String sessionId, String requestId) {
        if (!redisUtil.setIfAbsent(requestId, JsonUtils.toJson(new SessionCacheObject(sessionId, requestId))))
        {
            // cant aquire lock -> request is already being processed.
            throw new RequestCurrentlyProcessingExeption(requestId);
        }
    }
}
