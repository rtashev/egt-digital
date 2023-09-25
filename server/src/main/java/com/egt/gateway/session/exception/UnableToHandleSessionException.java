package com.egt.gateway.session.exception;

public class UnableToHandleSessionException extends RuntimeException {

    public UnableToHandleSessionException(String sessionId, String requestId)
    {
        super(String.format("Unable to process session %s and request %s", sessionId, requestId));
    }
}
