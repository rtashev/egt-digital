package com.egt.gateway.session.domain.request;

import lombok.Builder;

@Builder
public record FindSessionRequest(String requestId, Long sessionId) {
}
