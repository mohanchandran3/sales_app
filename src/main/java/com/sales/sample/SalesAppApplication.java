package com.sales.sample;

import java.sql.Connection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan(basePackages = "com.sales.sample")
@OpenAPIDefinition(info = @Info(title = "Sales App", version = "1.0", description = "Sales App Description."))
@Slf4j
public class SalesAppApplication {

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(SalesAppApplication.class, args);
	}

	@PostConstruct
    public void checkDatabaseConnection() {
        try (Connection conn = dataSource.getConnection()) {
            log.info("Database connection established successfully!");
        } catch (Exception e) {
            log.error("Failed to connect to database", e);
            throw new RuntimeException("Database connection failed", e);
        }
    }
}
