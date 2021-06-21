package com.proformainvoices.batch.process;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProcessApplication {


	public static void main(String[] args) {

		System.out.println("slight changes");
		SpringApplication.run(ProcessApplication.class, args);


	}

}
