package com.example.registration.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.registration.services.UserService;



@RestController
@RequestMapping("/api")
public class LoginController {

	private final UserService userService;

	public LoginController(UserService userService)
	{
		this.userService = userService;
	}

	@PostMapping("/login")
	public boolean login(@RequestBody LoginRequest request)
	{
		return userService.authenticate(request.getUsername(), request.getPassword());
	}

	public static class LoginRequest {
		private String username;
		private String password;

		public void setUsername(String username) { this.username = username;}
		public String getUsername() { return username; }

		public void setPassword(String password) { this.password = password;}
		public String getPassword() { return password; }
	}
}
