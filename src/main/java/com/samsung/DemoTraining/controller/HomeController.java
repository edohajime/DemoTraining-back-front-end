package com.samsung.DemoTraining.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samsung.DemoTraining.repository.model.User;
import com.samsung.DemoTraining.services.UserService;

@Controller
public class HomeController {
	@Autowired
	UserService userService;
	
	@GetMapping(value = "/")
	public String index(Authentication authenticate, Model model) {
		model.addAttribute("users", userService.getAllUser());
		model.addAttribute("username", authenticate.getName());
		model.addAttribute("authorities", authenticate.getAuthorities());
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

	@GetMapping(value = "/change-username")
	public String modUser(@RequestParam(name = "id") int id, Model model) {
		User user = userService.get(id);
		model.addAttribute("user", user);
		return "change-username";
	}
	
	@GetMapping(value = "/change-password")
	public String changePassword(Principal principal, Model model) {
		model.addAttribute("username", principal.getName());
		return "change-password";
	}
	
	@GetMapping(value = "/profile")
	public String profileInfo(Principal principal, Model model) {
		User user = userService.getUser(principal.getName());
		model.addAttribute("username", principal.getName());
		model.addAttribute("user", user);
		return "profile";
	}
	
	@GetMapping(value = "/view-profile")
	public String viewProfile(@RequestParam(name = "id") int id, Principal principal, Model model) {
		User user = userService.get(id);
		model.addAttribute("username", principal.getName());
		model.addAttribute("user", user);
		return "view-profile";
	}
	
	@GetMapping(value = "/update-profile")
	public String updateProfile(Principal principal, Model model) {
		User user = userService.getUser(principal.getName());
		model.addAttribute("username", principal.getName());
		model.addAttribute("user", user);
		return "update-profile";
	}

}
