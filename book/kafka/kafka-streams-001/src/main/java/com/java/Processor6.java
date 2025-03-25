package com.java;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.api.ContextualProcessor;
import org.apache.kafka.streams.processor.api.Record;

@Slf4j
public class Processor6 extends ContextualProcessor<String, String, String, String> {

    private final String nextOnTopology;
    public Processor6(String nextOnTopology) {
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
