package com.egt.gateway.session.exception;

public class RequestCurrentlyProcessingExeption extends RuntimeException{

    public RequestCurrentlyProcessingExeption(String requestId)
    {
        super(String.format("Request %s is being processed", requestId));
    }
}
