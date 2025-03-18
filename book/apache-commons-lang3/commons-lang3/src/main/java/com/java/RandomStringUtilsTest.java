package com.java;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class RandomStringUtilsTest {

    /**
     * randomString1=DWVxBY5jSi
     * randomString2=HsOnseHKUN
     * randomString3=wk1ILav9Eb
     */
    @Test
    void randomString() {
        boolean useLetters = true;
        boolean useNumbers = true;
        String randomString1 = RandomStringUtils.random(10, useLetters, useNumbers);
        log.info("randomString1={}", randomString1);

        String randomString2 = RandomStringUtils.randomAlphabetic(10);
        log.info("randomString2={}", randomString2);

        String randomString3 = RandomStringUtils.randomAlphanumeric(10);
        log.info("randomString3={}", randomString3);
    }
}
