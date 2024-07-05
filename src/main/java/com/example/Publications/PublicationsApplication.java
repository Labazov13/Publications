package com.example.Publications;

import com.example.Publications.configurations.RabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Import(RabbitConfig.class)
public class PublicationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicationsApplication.class, args);
	}

}
