package com.example.registration.model;

/**
 * Represents a registration request sent from the frontend
 * when a student attempts to register for a course.
 *
 * Student Persona Component:
 * - Supports RegistrationController
 * - Supports registration.js
 */
public class RegistrationRequest {

	private String username;
	private String courseId;

	public RegistrationRequest() {}

	/**
	 * Constructs a RegistrationRequest object.
	 *
	 * @param username the student's username
	 * @param courseId the ID of the course to register for
	 */
	public RegistrationRequest(String username, String courseId) {
		this.username = username;
		this.courseId = courseId;
	}

	public String getUsername() { return username; }
	public String getCourseId() { return courseId; }

	public void setUsername(String username) { this.username = username; }
	public void setCourseId(String courseId) { this.courseId = courseId; }
}

