package com.example.mppproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MppProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MppProjectApplication.class, args);
	}

	public String Hello(){
		return "Keman aneshe";
	}

}
