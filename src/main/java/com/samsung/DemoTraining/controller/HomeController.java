package com.samsung.DemoTraining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samsung.DemoTraining.repository.model.User;
import com.samsung.DemoTraining.services.UserServices;

@Controller
public class HomeController {
	@Autowired
	UserServices userService;
	
	@GetMapping(value = "/")
	public String index(Model model) {
		model.addAttribute("users", userService.getAllUser());
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
	public String modUser(@RequestParam(name = "id") int id, Model model) {
		User user = userService.get(id);
		model.addAttribute("user", user);
		return "mod-user";
	}

}
