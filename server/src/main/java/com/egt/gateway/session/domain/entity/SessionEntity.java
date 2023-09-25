package com.egt.gateway.session.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionEntity {
    private int id;
    private String sessionId;
    private String requestId;
    private String userId;
    private String apiResponse;
}
