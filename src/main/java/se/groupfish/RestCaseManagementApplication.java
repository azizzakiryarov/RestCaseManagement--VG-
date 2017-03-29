package se.groupfish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import se.groupfish.springcasemanagement.config.Config;

@SpringBootApplication
@Import(Config.class)
@ComponentScan(basePackages = {"se.groupfish.springcasemanagement", "se.groupfish.restcasemanagement"})
public class RestCaseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestCaseManagementApplication.class, args);
	}
}
