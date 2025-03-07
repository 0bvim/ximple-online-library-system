package com.ximple.library;

import com.ximple.library.user.User;
import com.ximple.library.user.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class LibraryApplication {

	private static final Logger logger = LoggerFactory.getLogger(LibraryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRestClient client) {
		return args -> {
			List<User> users = client.findAll();
			System.out.println("client = " + client);

			User byId = client.findById(42);
			System.out.println("byId = " + byId);

		};
	}

}
