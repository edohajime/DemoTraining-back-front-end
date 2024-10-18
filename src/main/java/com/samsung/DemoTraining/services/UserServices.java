package com.samsung.DemoTraining.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.samsung.DemoTraining.repository.model.User;

@Service
public class UserServices {
	public static List<User> users = new ArrayList<>();
	{
		users.addAll(Arrays.asList(User.builder().id(1).username("ht.anh1").password("123456").build(),
				User.builder().id(2).username("nd.anh2").password("234567").build(),
				User.builder().id(3).username("bt.tu3").password("345678").build(),
				User.builder().id(4).username("ktt.hang4").password("456789").build()));
	}
	public static int countUsers = 4;

	public void add(User user) {
		users.add(user);
	}

	public boolean contain(User checkUser) {
		boolean contain = false;
		for (User user : users) {
			if (user.getUsername().equals(checkUser.getUsername())) {
				contain = true;
			}
		}
		return contain;
	}

	public void updateUserName(int id, String username) {
		users.get(id - 1).setUsername(username);
	}

	public void updatePassword(int id, String password) {
		users.get(id - 1).setPassword(password);
		
	}
	
	public User get(int id) {
		return users.get(id - 1);
	}
}
