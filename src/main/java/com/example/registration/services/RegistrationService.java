package com.example.registration.services;

import com.example.registration.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 *     <li>Email notification previews are created for registration outcomes</li>
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

    private final Map<String, List<Course>> studentRegistrations = new HashMap<>();
    private final Map<String, Integer> courseEnrollmentCounts = new HashMap<>();
    private final Map<String, Integer> courseCapacityLimits = new HashMap<>();
    private final EmailNotificationService emailNotificationService;

    /**
     * Constructor for dependency injection of EmailNotificationService.
     *
     * @param emailNotificationService service used to prepare email notifications
     */

        public RegistrationService(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;

        /*
         * Sample maximum course capacities.
         * These can be adjusted later if the team connects capacity to MySQL.
         */
        courseCapacityLimits.put("CMSC495", 2);
        courseCapacityLimits.put("CMSC451", 2);

        courseEnrollmentCounts.put("CMSC495", 0);
        courseEnrollmentCounts.put("CMSC451", 0);
	}

	/**
	 * Registers a student for a course.
	 *
	 * <p>This method simulates course registration by creating a placeholder
	 * Course object and adding it to an in-memory list. The method always
	 * returns {@code true} to indicate success during development.</p>
	 *
	 * @param username the student's username
	 * @param courseId the ID of the course to register for
	 * @return {@code true} if registration succeeds false otherwise
	 */

  public boolean registerStudent(String username, String courseId) {
        Course course = buildCourseFromId(courseId);

        if (course == null) {
            emailNotificationService.sendNotification(
                    username,
                    "Course Registration Failed",
                    "Registration failed because course " + courseId + " was not found."
            );

            return false;
        }

        if (isAlreadyRegistered(username, courseId)) {
            emailNotificationService.sendNotification(
                    username,
                    "Course Registration Failed",
                    "Registration failed because you are already registered for " + courseId + "."
            );

            return false;
        }

        if (!hasMetPrerequisites(username, courseId)) {
            emailNotificationService.sendNotification(
                    username,
                    "Course Registration Failed",
                    "Cannot add " + courseId + " due to Prerequisite not met: CMSC 345."
            );

            return false;
        }

        if (isCourseFull(courseId)) {
            emailNotificationService.sendNotification(
                    username,
                    "Course Registration Failed",
                    "Registration failed because " + courseId + " has reached maximum student capacity."
            );

            return false;
        }

        studentRegistrations
                .computeIfAbsent(username, key -> new ArrayList<>())
                .add(course);

        courseEnrollmentCounts.put(
                courseId.toUpperCase(),
                courseEnrollmentCounts.getOrDefault(courseId.toUpperCase(), 0) + 1
        );

        emailNotificationService.sendNotification(
                username,
                "Course Registration Confirmation",
                "You have successfully registered for " + course.getId() + " - " + course.getName() + "."
        );

        return true;
    }

    /**
     * Withdraws a student from a registered course.
     *
     * @param username the student's username
     * @param courseId the ID of the course to withdraw from
     * @return true if withdrawal succeeds, false otherwise
     */
    public boolean withdrawStudent(String username, String courseId) {
        List<Course> registeredCourses = studentRegistrations.get(username);

        if (registeredCourses == null || registeredCourses.isEmpty()) {
            emailNotificationService.sendNotification(
                    username,
                    "Course Withdrawal Failed",
                    "Withdrawal failed because you are not registered for " + courseId + "."
            );

            return false;
        }

        Course courseToRemove = null;

        for (Course course : registeredCourses) {
            if (course.getId().equalsIgnoreCase(courseId)) {
                courseToRemove = course;
                break;
            }
        }

        if (courseToRemove == null) {
            emailNotificationService.sendNotification(
                    username,
                    "Course Withdrawal Failed",
                    "Withdrawal failed because " + courseId + " was not found in your registered courses."
            );

            return false;
        }

        registeredCourses.remove(courseToRemove);

        String normalizedCourseId = courseId.toUpperCase();
        int currentCount = courseEnrollmentCounts.getOrDefault(normalizedCourseId, 0);

        if (currentCount > 0) {
            courseEnrollmentCounts.put(normalizedCourseId, currentCount - 1);
        }

        emailNotificationService.sendNotification(
                username,
                "Course Withdrawal Confirmation",
                "You have successfully withdrawn from " + courseToRemove.getId() + " - " + courseToRemove.getName() + "."
        );

        return true;
    }

    /**
     * Retrieves all courses registered by a specific student.
     *
     * @param username the student's username
     * @return list of registered courses
     */
    public List<Course> getCoursesForStudent(String username) {
        return studentRegistrations.getOrDefault(username, new ArrayList<>());
    }

    /**
     * Checks whether the student is already registered for a course.
     *
     * @param username the student's username
     * @param courseId the course ID to check
     * @return true if already registered, false otherwise
     */
    private boolean isAlreadyRegistered(String username, String courseId) {
        List<Course> registeredCourses = studentRegistrations.get(username);

        if (registeredCourses == null) {
            return false;
        }

        for (Course course : registeredCourses) {
            if (course.getId().equalsIgnoreCase(courseId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the selected course has reached capacity.
     *
     * @param courseId the course ID to check
     * @return true if course is full, false otherwise
     */
    private boolean isCourseFull(String courseId) {
        String normalizedCourseId = courseId.toUpperCase();

        int enrolledCount = courseEnrollmentCounts.getOrDefault(normalizedCourseId, 0);
        int capacityLimit = courseCapacityLimits.getOrDefault(normalizedCourseId, 30);

        return enrolledCount >= capacityLimit;
    }

    /**
     * Validates prerequisite rules for selected courses.
     *
     * <p>MaryWashington has not completed CMSC 345, so she cannot
     * register for CMSC495. JaneSmith is allowed to register for CMSC495.</p>
     *
     * @param username the student's username
     * @param courseId the selected course ID
     * @return true if prerequisites are met, false otherwise
     */
    private boolean hasMetPrerequisites(String username, String courseId) {
        if (courseId.equalsIgnoreCase("CMSC495")
                && username.equalsIgnoreCase("MaryWashington")) {
            return false;
        }

        return true;
    }

    /**
     * Creates course information for known sample courses.
     *
     * @param courseId the selected course ID
     * @return Course object if found, otherwise null
     */
    private Course buildCourseFromId(String courseId) {
        if (courseId.equalsIgnoreCase("CMSC495")) {
            return new Course(
                    "CMSC495",
                    "Capstone",
                    "Dr. Smith",
                    3,
                    "MWF 10AM"
            );
        }

        if (courseId.equalsIgnoreCase("CMSC451")) {
            return new Course(
                    "CMSC451",
                    "Algorithms",
                    "Dr. Lee",
                    3,
                    "TTh 2PM"
            );
        }

        return null;
    }
}
