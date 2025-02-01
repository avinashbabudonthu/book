package com.practice.java.lang;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class RuntimeTest {

    /**
     * [RuntimeTest.basicMethods] - runtime=java.lang.Runtime@61d6015a
     * [RuntimeTest.basicMethods] - version=21.0.6+8-LTS-188
     * [RuntimeTest.basicMethods] - version.version=[21, 0, 6]
     * [RuntimeTest.basicMethods] - version.feature=21
     * [RuntimeTest.basicMethods] - version.interim=0
     * [RuntimeTest.basicMethods] - version.update=6
     * [RuntimeTest.basicMethods] - version.build=Optional[8]
     */
    @Test
    void basicMethods() {
        Runtime runtime = Runtime.getRuntime();
        Runtime.Version version = Runtime.version();

        log.info("runtime={}", runtime);
        log.info("version={}", version);
        log.info("version.version={}", version.version());
        log.info("version.feature={}", version.feature());
        log.info("version.interim={}", version.interim());
        log.info("version.update={}", version.update());
        log.info("version.build={}", version.build());
    }

    /**
     * program started
     * waiting 3 seconds
     * program completed
     * shutdown hook 1
     * shutdown hook 2
     */
    @SneakyThrows
    @Test
    void shutdownHooks1() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdown hook 1");
        }));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdown hook 2");
        }));

        System.out.println("program started");
        System.out.println("waiting 3 seconds");
        Thread.sleep(1000 * 3);
        System.out.println("program completed");
    }

    /**
     * INFO  com.java.lang.ShutdownHook2:29: i=0
     * INFO  com.java.lang.ShutdownHook2:29: i=1
     * INFO  com.java.lang.ShutdownHook2:29: i=2
     * INFO  com.java.lang.ShutdownHook2:29: i=3
     * INFO  com.java.lang.ShutdownHook2:29: i=4
     * shutdown hook 2
     * shutdown hook 3
     * shutdown hook 4
     * shutdown hook 1
     *
     */
    @SneakyThrows
    @Test
    void shutdownHooks2() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdown hook 1");
        }));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdown hook 2");
        }));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdown hook 3");
        }));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdown hook 4");
        }));

        for(int i=0;i<5;i++){
            Thread.sleep(1000);
            log.info("i={}", i);
            if(i == 4)
                System.exit(1);
        }
    }
}
