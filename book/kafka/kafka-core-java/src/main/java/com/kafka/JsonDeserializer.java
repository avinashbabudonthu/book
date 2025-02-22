package com.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

@Slf4j
public class JsonDeserializer implements Deserializer<Object> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public Object deserialize(String topic, byte[] data) {
        String jsonString = new String(data);
        Object result = null;
        try {
            result = OBJECT_MAPPER.readValue(jsonString, Employee.class);
        } catch (JsonProcessingException e) {
            log.error("Exception while deserialization", e);
        }

        return result;
    }

}