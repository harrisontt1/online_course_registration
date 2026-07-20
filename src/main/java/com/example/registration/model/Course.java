package com.example.registration.model;

/**
 * Represents a course available for registration.
 * This model is used by both the Courses page and the
 * Registration system to display course details.
 *
 * Student Persona Component:
 * - Supports RegistrationService
 * - Supports RegistrationController
 * - Supports courses.js
 */
public class Course {

	private String id;
	private String name;
	private String instructor;
	private int credits;
	private String meetingTime;

	/**
	 * Constructs a Course object.
	 * Represents a single course object in the course catalog.
	 * <p>This model is used by both the Course page and the
	 * Registration workflow to display course details.</p>
	 * @param id          course identifier
	 * @param name        course title
	 * @param instructor  instructor name
	 * @param credits     number of credits
	 * @param meetingTime scheduled meeting time
	 */
	public Course(String id, String name, String instructor, int credits, String meetingTime) {
		this.id = id;
		this.name = name;
		this.instructor = instructor;
		this.credits = credits;
		this.meetingTime = meetingTime;
	}

	public String getId() { return id; }
	public String getName() { return name; }
	public String getInstructor() { return instructor; }
	public int getCredits() { return credits; }
	public String getMeetingTime() { return meetingTime; }

	public void setId(String id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setInstructor(String instructor) { this.instructor = instructor; }
	public void setCredits(int credits) { this.credits = credits; }
	public void setMeetingTime(String meetingTime) { this.meetingTime = meetingTime; }
}
