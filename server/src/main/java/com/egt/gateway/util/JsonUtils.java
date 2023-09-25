package com.egt.gateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils
{
    private final static ObjectMapper objectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        configureMapper(objectMapper);
        return objectMapper;
    }

    public static void configureMapper(ObjectMapper objectMapper)
    {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static  <T> T fromJson(String json, Class<T> clazz)
    {
        try
        {
            return objectMapper.readValue(json, clazz);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String toJson(Object o)
    {
        try
        {
            return objectMapper.writeValueAsString(o);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
