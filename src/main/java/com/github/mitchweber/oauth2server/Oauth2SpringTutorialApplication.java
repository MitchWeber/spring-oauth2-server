package com.github.mitchweber.oauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Oauth2SpringTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2SpringTutorialApplication.class, args);
	}

}
