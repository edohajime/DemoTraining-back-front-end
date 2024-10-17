package com.samsung.DemoTraining.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samsung.DemoTraining.repository.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
