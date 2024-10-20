package com.samsung.DemoTraining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

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

		if (!userService.contain(user)) {
			if (!user.getPassword().equals(user.getConfirm_password())) {
				model.addAttribute("error", "Confirm password must equals to new password!");
				return "add-user";
			} 
			
			User user1 = User.builder().username(user.getUsername()).password(user.getPassword())
					.build();
			userRepo.save(user1);
//			userService.add(user1);
			
			model.addAttribute("message", "Register successful");
			return "redirect:/";
		} else {
			model.addAttribute("error", "Username existed");
			return "add-user";
		}
	}
	
	@PostMapping(value = "/modify-user")
	public String modifyUser(User user, Model model) {
		if (!user.getPassword().equals(user.getConfirm_password())) {
			model.addAttribute("error", "Confirm password must equals to new password!");
			return "mod-user";
		} 
		
		User userUpdate = userService.get(user.getId());
		
		// neu username ko ton tai thi sua username va password, neu ton tai thi chi sua password
		if (!userService.contain(user)) {
			userUpdate.setUsername(user.getUsername());
			userUpdate.setPassword(user.getPassword());
			
//			userService.updateUserName(user.getId(), user.getUsername());
//			userService.updatePassword(user.getId(), user.getUsername());
		} else {
			userUpdate.setPassword(user.getPassword());
//			userService.updatePassword(user.getId(), user.getUsername());			
		}

		userRepo.save(userUpdate);
		
		model.addAttribute("message", "Modify successful");
		return "redirect:/";
		
	}
}
