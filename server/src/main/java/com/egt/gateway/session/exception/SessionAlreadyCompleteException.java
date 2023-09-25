package com.egt.gateway.session.exception;

public class SessionAlreadyCompleteException extends RuntimeException{

    public SessionAlreadyCompleteException(String sessionId, String requestId)
    {
        super(String.format("Session is already complete for %s and request %s", sessionId, requestId));
    }
}
