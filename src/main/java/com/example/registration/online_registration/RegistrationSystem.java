package com.example.registration.online_registration;

import java.util.ArrayList;
import java.util.List;

public class RegistrationSystem {
	private List<Student> students;
	private List<Course> courses;
	private List<Section> activeSections;

	public RegistrationSystem() {
		this.students = new ArrayList<>();
		this.courses = new ArrayList<>();
		this.activeSections = new ArrayList<>();
	}

	/**
	 * Registers a student for a specific section after validating capacity and rules.
	 * Maps to Slide 4: register(student, section)
	 */
	public RegistrationResult register(Student student, Section section) {
		if (student == null || section == null) {
		return new RegistrationResult(false, "Invalid transaction context parameters.");
		}

		// Rule Check 1: Seat Capacity Availability
		if (!section.checkAvailability()) {
		return new RegistrationResult(false, "Section is completely full. Cap reached.");
		}

		// Rule Check 2: Prerequisites Validation
		if (!section.validateRequirements(student)) {
		return new RegistrationResult(false, "Prerequisites not met for this course. Contact your academic advisor.");
		}

		// If all rules pass, execute enrollment state change
		section.enrollStudent(student);

		// Natively compatible with either casing choice
		return new RegistrationResult(true, "Registration complete! You have successfully enrolled in " + section.getSectionID());
	}

	/**
	 * Authenticates whether a student exists in the system context.
	 * Maps to Slide 4: AuthenticateStudent()
	 */
	public boolean authenticateStudent(String studentID) {
		if (studentID == null) {
		return false;
		}
		// Gracefully accepts calls via the getStudentID() alias hook
		return students.stream()
				.anyMatch(s -> s.getStudentID() != null && s.getStudentID().equals(studentID));
	}

	// Infrastructure helper methods to safely populate system collections
	public void addSection(Section section) {
		if (section != null) {
		this.activeSections.add(section);
		}
	}

	public void addStudent(Student student) {
		if (student != null) {
		this.students.add(student);
		}
	}

	public void addCourse(Course course) {
		if (course != null) {
		this.courses.add(course);
		}
	}
}