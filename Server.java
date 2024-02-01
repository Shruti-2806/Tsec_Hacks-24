package com.example.hacks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Server {
	
	static boolean jain = false;
	static boolean halal = false;
	static boolean kosher = false;
	static boolean nuts = false;
	static boolean soy = false;
	static boolean dairy = false;
	static boolean gluten = false;
	
	public static void main(String args[]) {
		SpringApplication.run(Server.class, args);
	}
}