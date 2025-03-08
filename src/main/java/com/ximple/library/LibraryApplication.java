package com.ximple.library;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@OpenAPIDefinition(
    info = @Info(title = "Online Library API", version = "1.0",
        description = "Online library system with book search, reservations, and reviews"))

public class LibraryApplication {
  public static void main(String[] args) {
    SpringApplication.run(LibraryApplication.class, args);
  }
}
