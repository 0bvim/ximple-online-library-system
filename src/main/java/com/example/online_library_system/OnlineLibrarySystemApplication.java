package com.example.online_library_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineLibrarySystemApplication {

	public static void main(String[] args) {
		final String a = "Test";
		System.out.println(a);
		SpringApplication.run(OnlineLibrarySystemApplication.class, args);
	}


}
