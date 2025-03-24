package com.java;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.To;

@Slf4j
public class Example4Processor1 extends AbstractProcessor<String, String> {

    private final String nextOnTopology;
    public Example4Processor1(String nextOnTopology) {
        this.nextOnTopology = nextOnTopology;
    }

    @Override
    public void process(String key, String value) {
        log.info("key={}, value={}", key, value);
        context.forward(key, value, To.child(nextOnTopology));
    }
}
