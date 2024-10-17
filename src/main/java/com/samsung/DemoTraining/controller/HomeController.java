package com.samsung.DemoTraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping(value = "/")
	public String index() {
		return "index";
	}
	
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	@GetMapping(value = "/add-user")
	public String addUser() {
		return "add-user";
	}
	
	@GetMapping(value = "/mod-user")
	public String modUser() {
		return "mod-user";
	}

}
