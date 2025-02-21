package com.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.util.TimeZone;

@Slf4j
public class JsonSerializer implements Serializer<Object> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()/*.setTimeZone(TimeZone.getTimeZone("EST"))*/;

    @Override
    public byte[] serialize(String topic, Object data) {
        byte[] result = null;
        if(null != data) {
            try {
                String json = OBJECT_MAPPER.writeValueAsString(data);
                result = json.getBytes();
            } catch (JsonProcessingException e) {
                log.error("Exception converting object to JSON", e);
            }
        }
        return result;
    }
}
