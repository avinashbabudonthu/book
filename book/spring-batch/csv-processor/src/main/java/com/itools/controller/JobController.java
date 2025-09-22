package com.itools.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class JobController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @GetMapping(value = "/run-job")
    public String launchJob() {
        try {
            JobParameters jobParameters = new JobParameters();
            jobLauncher.run(job, jobParameters);
            return "Job launched successfully.";
        } catch (Exception e) {
            String error = "Failed to launch job: " + e.getMessage();
            log.error(error, e);
            return error;
        }
    }
}