package com.egt.gateway.session.domain.request;

import lombok.Builder;

@Builder
public record InsertSessionRequest(
    String requestId,
    long sessionId,
    String producerId,
    long timestamp
) {
}
