package dev.cydome.awesomepizza;

import org.springframework.boot.SpringApplication;

public class TestAwsomepizzaApplication {

	public static void main(String[] args) {
		SpringApplication.from(AwsomepizzaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
