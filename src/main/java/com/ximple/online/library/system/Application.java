package com.ximple.online.library.system;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@OpenAPIDefinition(
		info = @Info(title = "Online Library System", version = "1.0", description = "Documentation Online Library System API")
)

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
