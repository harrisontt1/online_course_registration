package com.example.registration.controller;

import org.springframework.web.bind.annotation.*;
import com.example.registration.services.StudentService;
import com.example.registration.model.Student;

/**
 * Controller responsible for exposing student profile information
 * to the frontend dashboard.
 *
 * <p>This controller provides the REST endpoint used by dashboard.js
 * to retrieve the authenticated student's profile data, including
 * name and student ID. It acts as the backend entry point for all
 * student identity-related information.</p>
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *     <li>Supplies student name and ID to dashboard.html</li>
 *     <li>Supports the login → dashboard workflow</li>
 *     <li>Acts as the backend source of truth for student identity</li>
 * </ul>
 *
 * <p><strong>Current Implementation Notes:</strong></p>
 * <ul>
 *     <li>Student data is currently stubbed in {@link StudentService}</li>
 *     <li>No database integration is present yet</li>
 *     <li>Username is passed from sessionStorage on the frontend</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *     <li>Integrate with a persistent database</li>
 *     <li>Support additional profile fields (email, major, GPA)</li>
 *     <li>Add endpoints for updating student profile information</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {

	private final StudentService studentService;

	/**
	 * Constructor for dependency injection of StudentService.
	 *
	 * @param studentService service used to retrieve student profile data
	 */
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	/**
	 * Retrieves a student's profile information based on username.
	 *
	 * <p>This endpoint is called by dashboard.js immediately after
	 * page load. The username is retrieved from sessionStorage on
	 * the frontend and passed to this method via the URL path.</p>
	 *
	 * @param username the username of the student
	 * @return Student object containing name, ID, and profile details
	 */
	@GetMapping("/{username}")
	public Student getStudent(@PathVariable String username) {
		return studentService.findByUsername(username);
	}
}
