package com.samsung.DemoTraining.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samsung.DemoTraining.configuration.WebSecurityConfig;
import com.samsung.DemoTraining.repository.UserRepository;
import com.samsung.DemoTraining.repository.model.User;
import com.samsung.DemoTraining.services.UserService;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepo;

	@Autowired
	UserService userService;

	@Autowired
	WebSecurityConfig config;

	@PostMapping(value = "/register")
	public String register(User user, Model model) {

		if (userService.contain(user)) {
			model.addAttribute("error", "Username existed");
			return "add-user";
		}

		if (!user.getPassword().equals(user.getConfirm_password())) {
			model.addAttribute("error", "Confirm password must equals to new password!");
			return "add-user";
		}

		User userAdd = User.builder().username(user.getUsername())
				.password(config.passwordEncoder().encode(user.getPassword())).build();
		userRepo.save(userAdd);

		return "redirect:/?message=Register successful";
	}

	@PostMapping(value = "/modify-user")
	public String modifyUser(User user, Model model) {
//		if (!user.getPassword().equals(user.getConfirm_password())) {
//			model.addAttribute("error", "Confirm password must equals to new password!");
//			return "mod-user";
//		}

		User userUpdate = userService.get(user.getId());

		// username ko ton tai thi sua username
		if (!userService.contain(user)) {
			userUpdate.setUsername(user.getUsername());
		}

		userRepo.save(userUpdate);

		return "redirect:/?message=Modify successful";
	}

	@PostMapping(value = "/change-pwd")
	public String changePwd(User user, Model model) {
		if (!user.getPassword().equals(user.getConfirm_password())) {
			model.addAttribute("error", "Confirm password must equals to new password!");
			return "mod-user";
		}

		User userUpdate = userService.getUser(user.getUsername());

		// username ko ton tai thi sua username password, neu ton tai chi sua password
		if (!userService.contain(user)) {
			userUpdate.setUsername(user.getUsername());
			userUpdate.setPassword(config.passwordEncoder().encode(user.getPassword()));
		} else {
			userUpdate.setPassword(config.passwordEncoder().encode(user.getPassword()));
		}

		userRepo.save(userUpdate);

		return "redirect:/?message=Modify successful";
	}

	@GetMapping(value = "/del-user")
	public String deleteUser(@RequestParam(name = "id") int id, Model model) {
		User userDel = userService.get(id);

		userRepo.delete(userDel);

		return "redirect:/?message=Delete successful";
	}

	@PostMapping(value = "/update-profile")
	public String updateProfile(User user, Model model) {
		if (!user.getFullname().matches("^[\\w\\s]{4,30}")) {
			model.addAttribute("error", "Invalid fullname!");
			return "update-profile";
		}
		
		if (!user.getEmail().matches("^[\\w+.]*\\w+@\\w+.[a-z]*")) {
			model.addAttribute("error", "Invalid email!");
			return "update-profile";
		}
		
		if (!user.getPhone().matches("^\\d{10,15}")) {
			model.addAttribute("error", "Invalid phone!");
			return "update-profile";
		}

		User userUpdate = userService.getUser(user.getUsername());
		
		userUpdate.setFullname(user.getFullname());
		userUpdate.setBirthyear(user.getBirthyear());
		userUpdate.setEmail(user.getEmail());
		userUpdate.setPhone(user.getPhone());
		userUpdate.setBio(user.getBio());

		userRepo.save(userUpdate);

		return "redirect:/?message=Update successful";
	}
	
}
