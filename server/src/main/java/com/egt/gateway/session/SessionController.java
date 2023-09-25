package com.egt.gateway.session;

import com.egt.gateway.session.domain.request.FindSessionRequest;
import com.egt.gateway.session.domain.request.InsertSessionRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("public/v1/json_api/")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping(value = "insert",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void sessionInsert(@RequestBody InsertSessionRequest insertSessionRequest) {
        sessionService.insertSession(String.valueOf(insertSessionRequest.sessionId()),
                insertSessionRequest.requestId(), insertSessionRequest.producerId());
    }

    @GetMapping(value = "find",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getSession(@RequestBody FindSessionRequest findSessionRequest) {
        return sessionService.findRequestIds(String.valueOf(findSessionRequest.sessionId()),
                findSessionRequest.requestId());
    }
}
