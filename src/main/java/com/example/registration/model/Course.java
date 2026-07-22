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
    private int maxCapacity;
    private int enrolledCount;
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
   public Course() {
    }

    /**
     * Constructor used by existing course setup code.
     *
     * @param id course ID
     * @param name course name
     * @param instructor course instructor
     * @param credits number of credits
     * @param meetingTime meeting time
     */
    public Course(String id, String name, String instructor, int credits, String meetingTime) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.credits = credits;
        this.meetingTime = meetingTime;
        this.maxCapacity = 2;
        this.enrolledCount = 0;
    }

    /**
     * Constructor used when course capacity and enrolled count are included.
     *
     * @param id course ID
     * @param name course name
     * @param instructor course instructor
     * @param credits number of credits
     * @param meetingTime meeting time
     * @param maxCapacity maximum number of students allowed
     * @param enrolledCount current number of enrolled students
     */
    public Course(String id, String name, String instructor, int credits,
                  String meetingTime, int maxCapacity, int enrolledCount) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.credits = credits;
        this.meetingTime = meetingTime;
        this.maxCapacity = maxCapacity;
        this.enrolledCount = enrolledCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void setEnrolledCount(int enrolledCount) {
        this.enrolledCount = enrolledCount;
    }
}
