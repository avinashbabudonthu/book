package com.itools.component;

import com.itools.record.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void beforeJob(org.springframework.batch.core.JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        log.info("{} is starting...", jobName);
    }

    @Override
    public void afterJob(org.springframework.batch.core.JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        BatchStatus status = jobExecution.getStatus();
        log.info("{} has ended with status: {}", jobName, status);

        jdbcTemplate
                .query("SELECT first_name, last_name FROM person", new DataClassRowMapper<>(Person.class))
                .forEach(person -> log.info("Found {{}} in the database.", person));
    }

}