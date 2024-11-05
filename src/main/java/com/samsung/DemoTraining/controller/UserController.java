package com.samsung.DemoTraining.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samsung.DemoTraining.configuration.WebSecurityConfig;
import com.samsung.DemoTraining.repository.UserRepository;
import com.samsung.DemoTraining.repository.model.User;
import com.samsung.DemoTraining.services.UserService;
import com.samsung.DemoTraining.utilities.Utilities;

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

		if (!Utilities.validateUsernamePassword(user, model)) {
			return "add-user";
		}

		if (userService.contain(user)) {
			model.addAttribute("error", "Username existed");
			return "add-user";
		}

		User userAdd = User.builder().username(user.getUsername())
				.password(config.passwordEncoder().encode(user.getPassword())).build();
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		userAdd.setAuthorities(authorities);

		userRepo.save(userAdd);

		return "redirect:/?message=Register successful";
	}

	@PostMapping(value = "/modify-user")
	public String modifyUser(User user, Model model) {

		if (!Utilities.validateUsername(user, model)) {
			return "change-username";
		}

		User userUpdate = userService.get(user.getId());

		// username ko ton tai thi sua username
		if (!userService.contain(user)) {
			userUpdate.setUsername(user.getUsername());
		}

		userRepo.save(userUpdate);

		return "redirect:/?message=Modify successful";
	}

	@PostMapping(value = "/change-pwd")
	public String changePwd(User user, Model model, Principal principal) {

		if (!Utilities.validateUsernamePassword(user, model)) {
			model.addAttribute("username", principal.getName());
			return "change-password";
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
	public String updateProfile(User user, Model model, Principal principal) {

		if (!Utilities.validateUserProfiles(user, model)) {
			model.addAttribute("username", principal.getName());
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

	@GetMapping(value = "/search-user")
	@ResponseBody
	public ResponseEntity<List<User>> searchUser(@RequestParam(name = "searchName") String searchName) {
		List<User> results = userService.getUsersBySearchName(searchName);
		
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

}
