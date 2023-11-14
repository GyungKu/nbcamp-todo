package com.sparta.nbcamptodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NbcampTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NbcampTodoApplication.class, args);
	}

}
