package com.samsung.DemoTraining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samsung.DemoTraining.repository.UserRepository;
import com.samsung.DemoTraining.repository.model.User;
import com.samsung.DemoTraining.services.UserServices;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepo;

	@Autowired
	UserServices userService;

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

		User userAdd = User.builder().username(user.getUsername()).password(user.getPassword()).build();
		userRepo.save(userAdd);

		model.addAttribute("message", "Register successful");
		return "redirect:/";
	}

	@PostMapping(value = "/modify-user")
	public String modifyUser(User user, Model model) {
		if (!user.getPassword().equals(user.getConfirm_password())) {
			model.addAttribute("error", "Confirm password must equals to new password!");
			return "mod-user";
		}

		User userUpdate = userService.get(user.getId());

		// username ko ton tai thi sua username password, neu ton tai chi sua password
		if (!userService.contain(user)) {
			userUpdate.setUsername(user.getUsername());
			userUpdate.setPassword(user.getPassword());
		} else {
			userUpdate.setPassword(user.getPassword());
		}

		userRepo.save(userUpdate);

		model.addAttribute("message", "Modify successful");
		return "redirect:/";
	}
	
	@GetMapping(value = "/del-user")
	public String deleteUser(@RequestParam(name = "id") int id, Model model) {
		User userDel = userService.get(id);
		
		userRepo.delete(userDel);
		
		model.addAttribute("message", "Delete successful");
		return "redirect:/";
	}
}
