package com.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan({"com.work"})
@EnableJpaAuditing
public class SpringFullProjectApplicationStart {
	public static void main(String[] args) {
		SpringApplication.run(SpringFullProjectApplicationStart.class, args);
	}

}