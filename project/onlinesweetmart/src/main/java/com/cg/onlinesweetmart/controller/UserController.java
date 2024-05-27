package com.cg.onlinesweetmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinesweetmart.entity.User;
import com.cg.onlinesweetmart.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@PreAuthorize("permitAll()")
	@GetMapping 
	public List<User> showAllUsers() {
		return userServiceImpl.showAllUsers();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public User updateUser(@RequestBody User user) {
		return userServiceImpl.updateUser(user);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public String cancelUser(@PathVariable (value = "id") long userId) {
		userServiceImpl.cancelUser(userId);
		return "deleted";
	}
}
