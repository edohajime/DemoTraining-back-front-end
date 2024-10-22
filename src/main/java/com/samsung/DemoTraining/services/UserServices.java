package com.samsung.DemoTraining.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.DemoTraining.repository.UserRepository;
import com.samsung.DemoTraining.repository.model.User;

@Service
public class UserServices {
	@Autowired
	private UserRepository userRepo;
	
	public User get(int id) {
		return userRepo.findById(id).get();
	}
	
	public List<User> getAllUser() {
		return userRepo.findAll(); 
	}
	
	/**
	 * Check username exists on database or not
	 * @param checkUser
	 * @return
	 */
	public boolean contain(User checkUser) {
		boolean contain = false;
		List<User> users = userRepo.findAll();
		for (User user : users) {
			if (user.getUsername().equals(checkUser.getUsername())) {
				contain = true;
			}
		}
		return contain;
	}
}
