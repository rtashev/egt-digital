package com.egt.gateway.statistics.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record UserStatistics(String userId, List<String> sessionId) {
}
