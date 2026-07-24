package com.example.registration.services;

import com.example.registration.model.Course;
import com.example.registration.model.RegistrationResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private final Map<String, Set<String>> completedCourses = new HashMap<>();

    private final EmailNotificationService emailNotificationService;
    private final CourseService courseService;

    /**
     * Constructor for RegistrationService.
     *
     * @param emailNotificationService service used to create email notification previews
     * @param courseService service used to manage course data and enrollment counts
     */
    public RegistrationService(EmailNotificationService emailNotificationService,
                               CourseService courseService) {
        this.emailNotificationService = emailNotificationService;
        this.courseService = courseService;

        /*
         * Sample completed course data:
         * MaryWashington has not completed CMSC 345.
         * JaneSmith has completed CMSC 345.
         */
        completedCourses.put("MaryWashington", new HashSet<>(List.of("CMSC451")));
        completedCourses.put("JaneSmith", new HashSet<>(List.of("CMSC345", "CMSC451")));
    }

    /**
     * Registers a student for a selected course.
     *
     * @param username the student's username
     * @param courseId the selected course ID
     * @return RegistrationResponse containing success status and message
     */
    public RegistrationResponse registerStudent(String username, String courseId) {
        Course course = courseService.findCourseById(courseId);

        if (course == null) {
            String message = "Registration failed because course " + courseId + " was not found.";

            emailNotificationService.sendNotification(
                    username,
                    "Course Registration Failed",
                    message
            );

            return new RegistrationResponse(false, message);
        }

        if (isAlreadyRegistered(username, courseId)) {
            String message = "Registration failed because you are already registered for " + courseId + ".";

            emailNotificationService.sendNotification(
                    username,
                    "Course Registration Failed",
                    message
            );

            return new RegistrationResponse(false, message);
        }

        if (!hasMetPrerequisites(username, course)) {
            String prerequisite = formatCourseId(course.getPrerequisiteCourseId());
            String message = "Cannot add " + course.getId()
                    + " due to Prerequisite not met: " + prerequisite + ".";

            emailNotificationService.sendNotification(
                    username,
                    "Course Registration Failed",
                    message
            );

            return new RegistrationResponse(false, message);
        }

        if (!courseService.hasAvailableSeat(courseId)) {
            String message = "Cannot add " + course.getId()
                    + " because the class is full.";

            emailNotificationService.sendNotification(
                    username,
                    "Course Registration Failed",
                    message
            );

            return new RegistrationResponse(false, message);
        }

        studentRegistrations
                .computeIfAbsent(username, key -> new ArrayList<>())
                .add(course);

        courseService.increaseEnrollment(courseId);

        String message = "You have successfully registered for "
                + course.getId() + " - " + course.getName() + ".";

        emailNotificationService.sendNotification(
                username,
                "Course Registration Confirmation",
                message
        );

        return new RegistrationResponse(true, message);
    }

    /**
     * Withdraws a student from a registered course.
     *
     * @param username the student's username
     * @param courseId the selected course ID
     * @return RegistrationResponse containing success status and message
     */
    public RegistrationResponse withdrawStudent(String username, String courseId) {
        List<Course> registeredCourses = studentRegistrations.get(username);

        if (registeredCourses == null || registeredCourses.isEmpty()) {
            String message = "Withdrawal failed because you are not registered for " + courseId + ".";

            emailNotificationService.sendNotification(
                    username,
                    "Course Withdrawal Failed",
                    message
            );

            return new RegistrationResponse(false, message);
        }

        Course courseToRemove = null;

        for (Course course : registeredCourses) {
            if (course.getId().equalsIgnoreCase(courseId)) {
                courseToRemove = course;
                break;
            }
        }

        if (courseToRemove == null) {
            String message = "Withdrawal failed because " + courseId
                    + " was not found in your registered courses.";

            emailNotificationService.sendNotification(
                    username,
                    "Course Withdrawal Failed",
                    message
            );

            return new RegistrationResponse(false, message);
        }

        registeredCourses.remove(courseToRemove);

        /*
         * This updates the available seat count after withdrawal.
         * Example: if seats were 1 / 2, withdrawing changes it back to 2 / 2.
         */
        courseService.decreaseEnrollment(courseId);

        String message = "You have successfully withdrawn from "
                + courseToRemove.getId() + " - " + courseToRemove.getName() + ".";

        emailNotificationService.sendNotification(
                username,
                "Course Withdrawal Confirmation",
                message
        );

        return new RegistrationResponse(true, message);
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
     * Checks whether a student is already registered for a selected course.
     *
     * @param username the student's username
     * @param courseId the selected course ID
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
     * Checks whether the student has completed the prerequisite for a course.
     *
     * @param username the student's username
     * @param course the selected course
     * @return true if prerequisites are met, false otherwise
     */
    private boolean hasMetPrerequisites(String username, Course course) {
        String prerequisiteCourseId = course.getPrerequisiteCourseId();

        if (prerequisiteCourseId == null || prerequisiteCourseId.isBlank()) {
            return true;
        }

        Set<String> completed = completedCourses.getOrDefault(username, new HashSet<>());

        return completed.contains(prerequisiteCourseId.toUpperCase());
    }

    /**
     * Formats a course ID for display.
     *
     * Example:
     * CMSC345 becomes CMSC 345
     *
     * @param courseId course ID
     * @return formatted course ID
     */
    private String formatCourseId(String courseId) {
        if (courseId == null || courseId.isBlank()) {
            return "None";
        }

        return courseId.replaceAll("([A-Z]+)([0-9]+)", "$1 $2");
    }
}
