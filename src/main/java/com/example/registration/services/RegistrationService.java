package com.example.registration.services;

import com.example.registration.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer responsible for managing student course registrations.
 *
 * <p>This class provides the backend logic used by the RegistrationController
 * to support both the course registration workflow and the dashboard summary
 * of registered courses. The current implementation uses in-memory storage
 * to simulate registration behavior while database integration is pending.</p>
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *     <li>Supports registration.js by processing course registration requests</li>
 *     <li>Supplies dashboard.js with the list of registered courses</li>
 *     <li>Acts as the backend source of truth for student course enrollment</li>
 * </ul>
 *
 * <p><strong>Current Implementation Notes:</strong></p>
 * <ul>
 *     <li>Registrations are stored in-memory and reset on application restart</li>
 *     <li>Course details are stubbed for development purposes</li>
 *     <li>No validation is performed on duplicate registrations</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *     <li>Integrate with a persistent database (MySQL/PostgreSQL)</li>
 *     <li>Store registrations per student instead of globally</li>
 *     <li>Validate course availability, prerequisites, and credit limits</li>
 *     <li>Support deregistration and schedule conflict detection</li>
 * </ul>
 */
@Service
public class RegistrationService {

	// Temporary in-memory storage until database integration
	private final List<Course> registeredCourses = new ArrayList<>();

	/**
	 * Registers a student for a course.
	 *
	 * <p>This method simulates course registration by creating a placeholder
	 * Course object and adding it to an in-memory list. The method always
	 * returns {@code true} to indicate success during development.</p>
	 *
	 * @param username the student's username
	 * @param courseId the ID of the course to register for
	 * @return {@code true} if registration succeeds
	 */
	public boolean registerStudent(String username, String courseId) {
		// TODO: Replace with real database lookup and validation
		Course course = new Course(
				courseId,
				"Placeholder Course",
				"Instructor",
				3,
				"MWF 10AM"
		);
		registeredCourses.add(course);
		return true;
	}

	/**
	 * Retrieves all courses registered by a specific student.
	 *
	 * <p>In the current implementation, registrations are not stored per
	 * student. This method returns the global list of registered courses
	 * for demonstration purposes.</p>
	 *
	 * @param username the student's username
	 * @return list of registered courses
	 */
	public List<Course> getCoursesForStudent(String username) {
		// TODO: Replace with per-student registration lookup
		return registeredCourses;
	}
}
