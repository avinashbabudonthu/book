package com.practice.java.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class LinkedHashMapTest {

    @Test
    public void create(){
        Map<Integer, String> map = new LinkedHashMap<>();
        log.info("map={}", map);
    }

    @Test
    public void put(){
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        log.info("map={}", map);
    }
}
