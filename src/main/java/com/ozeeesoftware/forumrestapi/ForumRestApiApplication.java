package com.ozeeesoftware.forumrestapi;

import com.ozeeesoftware.forumrestapi.config.ImageStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ImageStorageProperties.class})
public class ForumRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumRestApiApplication.class, args);
	}

}
