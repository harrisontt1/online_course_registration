package com.example.registration.service;

import com.example.registration.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer responsible for retrieving available course objects.
 *
 * <p>This class provides backend logic used by the CourseController
 * to supply the Course page with the current course catalog. The
 * implementation is currently stubbed for development and will be
 * replaced with database integration in future iterations.</p>
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *     <li>Supplies course objects to course.js</li>
 *     <li>Acts as the backend source of truth for course catalog data</li>
 * </ul>
 */
@Service
public class CourseService {

	/**
	 * Temporary in-memory list of course objects.
	 */
	private final List<Course> courseList = new ArrayList<>();

	public CourseService() {
		// Placeholder course objects
		courseList.add(new Course("CMSC495", "Capstone", "Dr. Smith", 3, "MWF 10AM"));
		courseList.add(new Course("CMSC451", "Algorithms", "Dr. Lee", 3, "TTh 2PM"));
	}

	/**
	 * Retrieves all available course objects.
	 *
	 * @return list of {@link Course} objects
	 */
	public List<Course> getAllCourse() {
		return courseList;
	}
}
