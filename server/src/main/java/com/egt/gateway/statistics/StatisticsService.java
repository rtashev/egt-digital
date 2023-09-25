package com.egt.gateway.statistics;

import com.egt.gateway.session.SessionRepository;
import com.egt.gateway.statistics.domain.UserStatistics;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final SessionRepository sessionRepository;

    public UserStatistics getUserStatistics(String userId) {
        var userSessions = sessionRepository.getUserSessions(userId);

        return UserStatistics.builder()
                .userId(userId)
                .sessionId(userSessions)
                .build();
    }
}
