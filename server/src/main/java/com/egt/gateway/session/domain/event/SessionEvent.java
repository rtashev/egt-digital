package com.egt.gateway.session.domain.event;

import com.egt.gateway.session.domain.entity.SessionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionEvent {
    private int id;
    private String sessionId;
    private String requestId;

    public static SessionEvent fromSessionEntity(SessionEntity sessionEntity)
    {
        return new SessionEventBuilder()
                                    .id(sessionEntity.getId())
                                    .sessionId(sessionEntity.getSessionId())
                                    .requestId(sessionEntity.getRequestId())
                                    .build();
    }
}
