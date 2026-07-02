package com.nick.javafundamentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the exercise workbook application.
 *
 * <p>Most exercises are plain Java (records, collections, concurrency) and do not
 * need this app running at all. The Spring Boot context exists so that the
 * Spring-flavored exercises ({@code SPR-*}, {@code DATA-*}, {@code PROD-*}) have a
 * real application to hang endpoints, beans, and repositories off of.
 *
 * <p>Run it with {@code ./mvnw spring-boot:run}. See the top-level README for the
 * full workflow.
 */
@SpringBootApplication
public class JavaFundamentalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaFundamentalsApplication.class, args);
    }
}
