package com.egt.gateway.external_service;

import com.egt.gateway.external_service.domain.ExternalServiceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalServiceClient {
    private final String externalServiceUrl;

    private final RestTemplate restTemplate;

    public ExternalServiceClient(
            @Value("${services.external-serivce.url}") String externalServiceUrl,
            RestTemplate restTemplate
    )
    {
        this.externalServiceUrl = externalServiceUrl;
        this.restTemplate = restTemplate;
    }


    public ExternalServiceResponse getExternalServiceResponse()
    {
        return restTemplate.getForObject(this.externalServiceUrl, ExternalServiceResponse.class);
    }


}
