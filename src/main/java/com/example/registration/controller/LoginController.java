package com.example.registration.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.registration.service.UserService;

/**
 * Controller responsible for handling user authentication requests.
 *
 * <p>This controller exposes the login endpoint used by the frontend
 * login page to validate user credentials. Upon successful authentication,
 * the frontend stores the username in sessionStorage and redirects the
 * student to the dashboard.</p>
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *     <li>Supports login.html and login.js</li>
 *     <li>Initiates the student workflow by validating credentials</li>
 *     <li>Acts as the entry point for all authenticated student actions</li>
 * </ul>
 *
 * <p><strong>Current Implementation Notes:</strong></p>
 * <ul>
 *     <li>Authentication logic is delegated to {@link UserService}</li>
 *     <li>No session tokens or cookies are generated server-side</li>
 *     <li>Frontend manages session state using sessionStorage</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *     <li>Integrate secure password hashing and storage</li>
 *     <li>Implement JWT-based authentication for API security</li>
 *     <li>Support role-based access (student, admin, instructor)</li>
 * </ul>
 */

@RestController
@RequestMapping("/api")
public class LoginController {

	private final UserService userService;

	/**
	 * Constructor for dependency injection of UserService.
	 *
	 * @param userService service responsible for validating user credentials
	 */

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Authenticates a user based on username and password.
	 *
	 * <p>This method receives a JSON payload from the frontend containing
	 * the username and password. It delegates authentication to the
	 * {@link UserService} and returns a boolean indicating success.</p>
	 *
	 * @param request LoginRequest containing username and password
	 * @return {@code true} if authentication succeeds, {@code false} otherwise
	 */

	@PostMapping("/login")
	public boolean login(@RequestBody LoginRequest request) {
		return userService.authenticate(request.getUsername(), request.getPassword());
	}

	/**
	 * Represents the login request payload sent from the frontend.
	 *
	 * <p>This inner class is used to deserialize the JSON body of the
	 * login request. It contains the username and password fields
	 * required for authentication.</p>
	 */

	public static class LoginRequest {
		private String username;
		private String password;

		public void setUsername(String username) { this.username = username;}
		public String getUsername() { return username; }

		public void setPassword(String password) { this.password = password;}
		public String getPassword() { return password; }
	}
}
