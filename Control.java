package com.example.hacks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Control {

	@GetMapping("/community")
	public String st() {
		return "community.html";
	}
	
	@GetMapping("/profile")
	public String st1() {
		return "profile.html";
	}
	
	@GetMapping("/stores")
	public String st2() {
		return "blog.html";
	}
}
