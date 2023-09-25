package com.egt.gateway.session;

import com.egt.gateway.session.domain.entity.SessionEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class SessionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public List<String> getRequestIdsForSession(String sessionId) {

        var sql = """
                SELECT request_id
                FROM user_sessions
                WHERE session_id = :session_id""";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("session_id", sessionId);

        return namedParameterJdbcTemplate.query(sql, params, extractRequestIdsResult());
    }

    public int insertSessionEntity(SessionEntity sessionEntity) {
        String sql = """
                      INSERT INTO user_sessions(session_id, request_id, user_id
                      ) VALUES (:session_id, :request_id, :user_id)
                      """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("session_id", sessionEntity.getSessionId())
                .addValue("request_id", sessionEntity.getRequestId())
                .addValue("user_id", sessionEntity.getUserId());

        var entityKeyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, params, entityKeyHolder);

        return entityKeyHolder.getKey().intValue();
    }

    public void updateSessionEntity(int id, String apiResponse) {
        String sql = """
                      UPDATE user_sessions
                      SET api_response = :api_response
                      WHERE id = :id
                      """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("api_response", apiResponse)
                .addValue("id", id);


        namedParameterJdbcTemplate.update(sql, params);
    }


    public List<String> getUserSessions(String userId) {
        var sql = """
                SELECT session_id
                FROM user_sessions
                WHERE user_id = :user_id""";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("user_id", userId);

        return namedParameterJdbcTemplate.query(sql, params, extractRequestIdsResult());
    }

    private static RowMapper<String> extractRequestIdsResult() {
        return (rs, rowNum) -> rs.getString(1);
    }
}
