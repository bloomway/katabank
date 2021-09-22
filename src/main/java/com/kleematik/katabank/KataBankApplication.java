package com.kleematik.katabank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KataBankApplication
{
	public static void main(String[] args)
	{
		System.out.println("launch application");
		SpringApplication.run(KataBankApplication.class, args);
	}

}
