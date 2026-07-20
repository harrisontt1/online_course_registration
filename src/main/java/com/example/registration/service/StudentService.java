package com.example.registration.service;

import com.example.registration.model.Student;
import org.springframework.stereotype.Service;

/**
 * Service responsible for retrieving student profile information.
 * Supports the dashboard by providing name and student ID.
 * <p>This class provides the backend logic used by the StudentController
 * to supply the dashboard with the authenticated student's profile data.
 * In the current implementation, student information is stubbed and
 * returned without database integration. This allows the frontend to
 * function while the persistence layer is under development.</p>
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *     <li>Supplies student name and ID to dashboard.js</li>
 *     <li>Supports authentication flow by providing identity details</li>
 *     <li>Acts as the backend source of truth for student profile data</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *     <li>Integrate with a database (MySQL/PostgreSQL)</li>
 *     <li>Retrieve full student records including email, major, GPA</li>
 *     <li>Support profile editing and persistence</li>
 * </ul>
 */
@Service
public class StudentService {

	/**
	 * Retrieves student information based on username.
	 *
	 * @param username the student's username
	 * @return Student object containing profile data
	 */
	public Student findByUsername(String username) {
		// TODO: Replace with real database lookup
		return new Student("12345", username, "Student Name");
	}
}
