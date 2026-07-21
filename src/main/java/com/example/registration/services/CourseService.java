package com.example.registration.services;

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

    private final List<Course> courseList = new ArrayList<>();

    public CourseService() {
        courseList.add(new Course(
                "CMSC495",
                "Capstone",
                "Dr. Smith",
                3,
                "MWF 10AM",
                2,
                0,
                "CMSC345"
        ));

        courseList.add(new Course(
                "CMSC451",
                "Algorithms",
                "Dr. Lee",
                3,
                "TTh 2PM",
                2,
                0,
                ""
        ));
    }

    public List<Course> getAllCourse() {
        return courseList;
    }

    public boolean addCourse(Course course) {
        if (findCourseById(course.getId()) != null) {
            return false;
        }

        course.setEnrolledCount(0);
        courseList.add(course);
        return true;
    }

    public boolean updateCourse(String courseId, Course updatedCourse) {
        Course existingCourse = findCourseById(courseId);

        if (existingCourse == null) {
            return false;
        }

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setInstructor(updatedCourse.getInstructor());
        existingCourse.setCredits(updatedCourse.getCredits());
        existingCourse.setMeetingTime(updatedCourse.getMeetingTime());
        existingCourse.setMaxCapacity(updatedCourse.getMaxCapacity());
        existingCourse.setPrerequisiteCourseId(updatedCourse.getPrerequisiteCourseId());

        return true;
    }

    public boolean deleteCourse(String courseId) {
        Course existingCourse = findCourseById(courseId);

        if (existingCourse == null) {
            return false;
        }

        courseList.remove(existingCourse);
        return true;
    }

    public Course findCourseById(String courseId) {
        for (Course course : courseList) {
            if (course.getId().equalsIgnoreCase(courseId)) {
                return course;
            }
        }

        return null;
    }

    public boolean hasAvailableSeat(String courseId) {
        Course course = findCourseById(courseId);

        if (course == null) {
            return false;
        }

        return course.getEnrolledCount() < course.getMaxCapacity();
    }

    public void increaseEnrollment(String courseId) {
        Course course = findCourseById(courseId);

        if (course != null) {
            course.setEnrolledCount(course.getEnrolledCount() + 1);
        }
    }

    public void decreaseEnrollment(String courseId) {
        Course course = findCourseById(courseId);

        if (course != null && course.getEnrolledCount() > 0) {
            course.setEnrolledCount(course.getEnrolledCount() - 1);
        }
    }
}
