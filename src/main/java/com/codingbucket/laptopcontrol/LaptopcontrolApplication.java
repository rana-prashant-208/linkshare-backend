package com.codingbucket.laptopcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LaptopcontrolApplication {

	public static void main(String[] args)
	{
		new SpringApplicationBuilder(LaptopcontrolApplication.class).headless(false).run(args);
	}

}
