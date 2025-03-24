package com.java;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.To;
import org.apache.kafka.streams.processor.api.ContextualProcessor;
import org.apache.kafka.streams.processor.api.Record;

@Slf4j
public class Example4Processor3 extends ContextualProcessor<String, String, String, String> {

    private final String nextOnTopology;
    public Example4Processor3(String nextOnTopology) {
        this.nextOnTopology = nextOnTopology;
    }

    @Override
    public void process(Record<String, String> record) {
        log.info("key={}, value={}, timestamp={}", record.key(), record.value(), record.timestamp());
        context().forward(record, nextOnTopology);
    }

    @Override
    public void close() {
        super.close();
    }
}
