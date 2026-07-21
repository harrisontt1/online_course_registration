package com.example.registration.controller;

import com.example.registration.model.Course;
import com.example.registration.services.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for exposing available course objects
 * to the frontend Course page.
 *
 * <p>This controller provides the REST endpoint used by course.js
 * to retrieve the list of available course objects. It supports
 * the student workflow by enabling course exploration prior to
 * registration.</p>
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *     <li>Supplies course objects to course.html</li>
 *     <li>Acts as the backend source of truth for course catalog data</li>
 *     <li>Supports the registration workflow</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/course")
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	/**
	 * Retrieves all available course objects.
	 *
	 * @return list of {@link Course} objects
	 */
	@GetMapping
	public List<Course> getAllCourse() {
		return courseService.getAllCourse();
	}
}
