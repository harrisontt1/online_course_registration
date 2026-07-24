package com.example.registration.controller;

import org.springframework.web.bind.annotation.*;
import com.example.registration.services.RegistrationService;
import com.example.registration.model.Course;
import com.example.registration.model.RegistrationRequest;
import com.example.registration.model.RegistrationResponse;

import java.util.List;

/**
 * Controller responsible for handling student course registration
 * and retrieving registered courses for display on the dashboard.
 *
 * Student Persona Component:
 * - Supports registration.js and dashboard.js
 * - Provides course registration functionality
 * - Provides course withdrawal functionality
 * - Supports email notification triggers through RegistrationService
 */
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    /**
     * Constructor for dependency injection of RegistrationService.
     *
     * @param registrationService service used to manage registrations
     */
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Retrieves all courses registered by a specific student.
     *
     * @param username the student's username
     * @return list of Course objects the student is registered for
     */
    @GetMapping("/{username}")
    public List<Course> getRegisteredCourses(@PathVariable String username) {
        return registrationService.getCoursesForStudent(username);
    }

    /**
     * Registers a student for a course.
     *
     * @param req RegistrationRequest containing username and courseId
     * @return true if registration succeeds, false otherwise
     */
   @PostMapping
    public RegistrationResponse register(@RequestBody RegistrationRequest req) {
        return registrationService.registerStudent(req.getUsername(), req.getCourseId());
    }


    /**
     * Withdraws a student from a course.
     *
     * @param req RegistrationRequest containing username and courseId
     * @return true if withdrawal succeeds, false otherwise
     */
    @DeleteMapping
    public RegistrationResponse withdraw(@RequestBody RegistrationRequest req) {
        return registrationService.withdrawStudent(req.getUsername(), req.getCourseId());
    }
}
