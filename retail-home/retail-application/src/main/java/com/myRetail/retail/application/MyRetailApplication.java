package com.myRetail.retail.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.myRetail")
public class MyRetailApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(MyRetailApplication.class, args);
	}

}
