package com.samsung.DemoTraining.utilities;

import java.util.Date;

import org.springframework.ui.Model;

import com.samsung.DemoTraining.repository.model.User;

public class Utilities {
	public static boolean validateUsernamePassword(User user, Model model) {

		boolean validatePassword = validatePassword(user, model);
		
		boolean validateUsername = validateUsername(user, model);
		
		if (!validateUsername || !validatePassword) 
			return false;
		
		return true;
	}

	public static boolean validateUsername(User user, Model model) {

		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			model.addAttribute("error", "Username is required");
			return false;
		}
		
		if (user.getUsername().length() <= 4 || user.getUsername().length() > 20) {
			model.addAttribute("error", "Username's length must between 4 and 20 characters!");
			return false;
		}
		
		if (!user.getUsername().matches("^[\\w\\d.]+")) {
			model.addAttribute("error", "Username not have any special character!");
			return false;
		}
		
		return true;
	}

	public static boolean validatePassword(User user, Model model) {

		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			model.addAttribute("error", "Password is required");
			return false;
		}

		if (!user.getPassword().equals(user.getConfirm_password())) {
			model.addAttribute("error", "Confirm password must equals to new password!");
			return false;
		}
		
		if (user.getPassword().length() > 20) {
			model.addAttribute("error", "Password can not greater than 20 characters!");
			return false;
		}
		
		return true;
	}

	public static boolean validateUserProfiles(User user, Model model) {

		boolean validateEmail = validateEmail(user, model);

		boolean validatePhone = validatePhone(user, model);

		boolean validateBirthyear = validateBirthyear(user, model);
 
		boolean validateFullname = validateFullname(user, model);
		
		if (!validateFullname || !validateBirthyear || !validateEmail || !validatePhone) 
			return false;
		
		return true;
	}

	public static boolean validateFullname(User user, Model model) {
		
		if (user.getFullname() == null || user.getFullname().isEmpty()) {
			model.addAttribute("error", "Fullname is required!");
			return false;
		}
		
		if (!user.getFullname().matches("^[a-zA-Z\\s]{4,30}")) {
			model.addAttribute("error", "Invalid fullname! Fullname just have alphabet character and length must in 4-30 character");
			return false;
		}
		
		return true;
	}
	
	public static boolean validateBirthyear(User user, Model model) {
		
		if (user.getBirthyear() == null) {
			model.addAttribute("error", "Birthyear is required!");
			return false;
		}
		
		Date now = new Date();
		if (user.getBirthyear() < 0 || (user.getBirthyear() - 1900) > now.getYear()) {
			model.addAttribute("error", "Birthyear can not be smaller than 0 or greater than current year");
			return false;
		}
		
		return true;
	}	
	
	public static boolean validateEmail(User user, Model model) {
		
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			model.addAttribute("error", "Email is required!");
			return false;
		}
		
		if (!user.getEmail().matches("^[\\w+.]*\\w+@\\w+.[a-z]*")) {
			model.addAttribute("error", "Invalid email!");
			return false;
		}
		
		return true;
	}
	
	public static boolean validatePhone(User user, Model model) {
		
		if (user.getPhone() == null || user.getPhone().isEmpty()) {
			model.addAttribute("error", "Phone is required!");
			return false;
		}
		
		if (!user.getPhone().matches("^\\d{10,15}")) {
			model.addAttribute("error", "Invalid phone!");
			return false;
		}
		
		return true;
	}
}
