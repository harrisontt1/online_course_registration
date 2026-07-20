package com.example.registration.model;

/**
 * Represents a student in the course registration system.
 * This model is used by the dashboard to display student
 * profile information such as name and student ID.
 *
 * Student Persona Component:
 * - Supports StudentController
 * - Supports dashboard.js
 */
public class Student {

	private String id;
	private String username;
	private String name;

	/**
	 * Constructs a Student object.
	 *
	 * @param id       unique student identifier
	 * @param username login username
	 * @param name     full student name
	 */
	public Student(String id, String username, String name) {
		this.id = id;
		this.username = username;
		this.name = name;
	}

	public String getId() { return id; }
	public String getUsername() { return username; }
	public String getName() { return name; }

	public void setId(String id) { this.id = id; }
	public void setUsername(String username) { this.username = username; }
	public void setName(String name) { this.name = name; }
}
