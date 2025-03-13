package com.java;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.AbstractProcessor;

@Slf4j
public class Example4Processor extends AbstractProcessor<String, String> {

    @Override
    public void process(String key, String value) {
        log.info("key={}, value={}", key, value);
    }
}
