package com.practice.java.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class SystemTest {

    /**
     * Get Java version <br />
     * Output:
     * java.specification.version=17
     */
    @Test
    @DisplayName("Get current running java version")
    void getJavaVersion() {
        log.info("java.specification.version={}", System.getProperty("java.specification.version"));
    }

    /**
     * Output:
     * current-time-in-milli-seconds=1723705107563
     */
    @Test
    @DisplayName(value = "Get current time in milli seconds")
    void currentTimeMillis() {
        log.info("current-time-in-milli-seconds={}", System.currentTimeMillis());
    }

    /**
     * Output:
     * Direct execution: temp-directory=C:\Users\[username]\AppData\Local\Temp\
     * <br />
     * With VM argument -Djava.io.tmpdir=C:\Temp: temp-directory=C:\Temp
     */
    @Test
    @DisplayName("Get temp directory path")
    void getTempDirectoryPath() {
        log.info("temp-directory={}", System.getProperty("java.io.tmpdir"));
    }

    /**
     * method to get line separator based on underlying operating system instead of hard coding
     */
    @Test
    public void lineSeparator() {
        // any of the below code is fine
        String lineSeparator = System.lineSeparator();
        // String lineSeparator = System.getProperty("line.separator");

        log.info("abc{}def", lineSeparator);
    }

    /**
     * variable system environment variables
     */
    @Test
    public void getPathEnvironmentVariable() {
        String path = System.getenv("PATH");
        log.info("path={}", path);
    }

    /**
     * passed as
     * -Dsys.prop.name=jack
     */
    @Test
    public void systemPropertyOrVMArgument() {
        String systemPropertyOrVMArgument = System.getProperty("sys.prop.name");
        log.info("value={}", systemPropertyOrVMArgument);
    }

}