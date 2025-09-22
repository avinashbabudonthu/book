package com.itools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	// @Scheduled(fixedRate = 5000)
	public void demoSchedule() {
		log.info("demoSchedule:: Scheduled task executed, {}, {}", Thread.currentThread().getName(), new Date());
	}

	// @Scheduled(fixedRate = 10000)
	public void demoSchedule2() {
		log.info("demoSchedule2:: Scheduled task executed, {}, {} ", Thread.currentThread().getName(), new Date());
	}
}
