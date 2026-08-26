package com.smarthr.employeeservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("Employee service started with a clean database. No dummy data seeded.");
    }
}
